module.exports = ItemsService;

/** @ngInject */
function ItemsService(Api, Util, $translate) {
  this.Api = Api;
  this.Util = Util;
  this.translate = $translate;
  this.query = '';
  this.loadedPages = {};
  this.numItems = 0;
  this.PAGE_SIZE = 80;
}

ItemsService.prototype = {
  load: function (query) {
    this.query = query;
    this.loadedPages = {};
    this.getItemAtIndex(0);
    return this;
  },
  getItemAtIndex: function (index) {
    var pageNumber = Math.floor(index / this.PAGE_SIZE);
    var page = this.loadedPages[pageNumber];
    if (angular.isDefined(page)) {
      return page[index % this.PAGE_SIZE];
    } else if (page !== null) {
      this.getItems(pageNumber);
    }
  },
  getLength: function () {
    return this.numItems;
  },
  getItems: function (pageNumber) {
    var self = this;
    this.loadedPages[pageNumber] = [];
    var pageOffset = pageNumber * this.PAGE_SIZE;
    if (pageOffset <= this.numItems) {
      this.Api.Data.get({ query: self.query, limit: pageOffset, size: this.PAGE_SIZE }, function (response) {
        if (response.$resolved) {
          self.numItems = response.result.length;
          var items = response.result.items;
          var key = Object.keys(items)[0];
          if (self.numItems > 1) {
            items = items[key];
            for (var i = 0; i < items.length; i++) {
              self.loadedPages[pageNumber].push(items[i]);
            }
          } else {
            self.loadedPages[pageNumber].push(items[key]);
          }
        } else {
          self.Util.loadToast(self.translate.instant('common.searchError'));
        }
      }, function (error) {
        self.Util.print(error);
        self.Util.loadToast(self.translate.instant('common.generalError'));
      });
    }
  }
};
