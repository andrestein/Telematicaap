module.exports = {
  template: require('./Virtual.html'),
  controller: Virtual
};
function Virtual($scope) {
  this.scope = $scope;
  $scope.states = ('Linux Windows').split(' ').map(function (state) {
    return { abbrev: state };
  });
}
