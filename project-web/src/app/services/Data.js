module.exports = DataService;
/** @ngInject */
function DataService($resource) {
  // var url = 'http://server/';
  var url = 'http://localhost:8080/Telematicaap/api/';
  return {
    Data: $resource(url.concat('data/:path'), { path: '@path' }, {
      send: {
        method: 'POST'
      }
    })
  };
}
