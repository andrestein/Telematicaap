/**
* formulario Module
*
* modulo para especificar los parametros de la maquina virtual
*/
angular.module('formulario', []).controller('virtualMachineCtrl', function(){
	this.vm = {};
	this.submit = function(){
		//TODO insertar los parametros en el script de terraform
		if (this.passwordConfirm === this.vm.password) {

		} else {
			alert("las contraseñas no son iguales");
		}
	};
});
