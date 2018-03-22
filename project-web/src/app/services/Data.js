module.exports = DataService;
/** @ngInject */
function DataService($resource) {
  // var url = 'http://server/';
  var url = 'http://localhost:8080/project/api/';
  return {
    Data: $resource(url.concat('data/:path'), { path: '@path' }, {
      save: {
        method: 'POST'
      },
      update: {
        method: 'PUT'
      },
      delete: {
        method: 'DELETE'
      },
      query: {
        method: 'POST',
        url: url.concat('data/get')
      },
      get: {
        method: 'GET',
        query: '@query',
        limit: '@limit',
        size: '@size',
        url: url.concat('data/:query/:limit/:size')
      },
      deleteDiskFile: {
        method: 'DELETE',
        url: url.concat('file/:entityid/:projectid')
      }
    })
  };
}
