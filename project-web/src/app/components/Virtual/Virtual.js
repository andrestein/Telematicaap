module.exports = {
  template: require('./Virtual.html'),
  controller: Entity
};
function Entity($scope) {
  this.scope = $scope;
}
