module.exports = UtilService;

/** @ngInject */
function UtilService($log, $mdToast, $mdDialog, $cookies, Api) {
  this.log = $log;
  this.mdToast = $mdToast;
  this.cookies = $cookies;
  this.mdDialog = $mdDialog;
  this.Api = Api;
}

UtilService.prototype = {
  print: function (message) {
    this.log.log(message);
  },
  loadToast: function (message) {
    var toast = this.mdToast.simple()
      .textContent(message)
      .position('bottom left')
      .hideDelay(3000)
      .action('OK')
      .highlightAction(true);
    this.mdToast.show(toast);
  },
  getDialog: function () {
    return this.mdDialog;
  }
};
