module.exports = configServer;

/** @ngInject */
function configServer($mdThemingProvider, $mdDateLocaleProvider, moment) {
  $mdThemingProvider.definePalette('amazingPaletteName', {
    50: 'ffebee',
    100: 'ffcdd2',
    200: 'ef9a9a',
    300: 'e57373',
    400: 'ef5350',
    500: 'BA0F16',
    600: 'e53935',
    700: 'd32f2f',
    800: 'c62828',
    900: 'BA0F16',
    A100: 'ff8a80',
    A200: 'ff5252',
    A400: 'ff1744',
    A700: 'd50000',
    contrastDefaultColor: 'light',
    contrastDarkColors: ['50', '100', '200', '300', '400', 'A100'],
    contrastLightColors: undefined
  });
  $mdThemingProvider.theme('default')
    .primaryPalette('amazingPaletteName');
  $mdDateLocaleProvider.formatDate = function (date) {
    return moment(date).format('MMM/YYYY');
  };
  // .accentPalette('orange');
  // .warnPalette();
  // .backgroundPalette();
}
