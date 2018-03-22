module.exports = {
  template: require('./Virtual.html'),
  controller: Entity
};
function Entity($scope, Api, Util) {
  this.scope = $scope;
  this.Api = Api;
  this.Util = Util;
}
