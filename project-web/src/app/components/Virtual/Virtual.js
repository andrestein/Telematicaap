module.exports = {
  template: require('./Virtual.html'),
  controller: Virtual
};
function Virtual($scope, Api, Util) {
  this.scope = $scope;
  this.Util = Util;
  this.Api = Api;
  $scope.states = ('Linux Windows').split(' ').map(function (state) {
    return { abbrev: state };
  });
}
Virtual.prototype = {
  getPath: function (clientId) {
    return 'cliente+' + clientId;
  },
  loadData: function () {
    var self = this;
    this.Util.print('HOLA');
    var data = this.scope.data;
    var path = this.getPath(data.clienSusbs.clientId);
    self.scope.showTable = false;
    self.scope.loading = true;
    if (angular.isDefined(data)) {
      self.Api.Data.save({ path: path }, data, function (response) {
        if (response.$resolved) {
          self.Util.loadToast('Los datos se enviaron con exito');
          self.cleanData();
        } else {
          self.Util.loadToast('No se pudieron enviar los datos');
        }
      }, function (error) {
        self.Util.print(error);
        self.Util.loadToast('Ha ocurrido un error vuelve a intentarlo');
      });
    }
  }
};
