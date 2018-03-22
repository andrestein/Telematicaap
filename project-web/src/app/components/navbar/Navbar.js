
module.exports = {
  template: require('./Navbar.html'),
  controller: Navbar
};
/** @ngInject */
function Navbar($scope) {
  this.scope = $scope;
}
