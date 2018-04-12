package rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import converter.XJMapper;
import util.Config;


@Path("/data")
public class DataRest {

	@POST
	@Path("/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addData(@PathParam("path") String path, String json) {
		JSONObject data = null;
		System.out.println(json);
		try {
			data = new JSONObject(json);
			JSONObject clienSusbs = data.getJSONObject("clienSusbs");
			JSONObject resource = data.getJSONObject("resource");
			JSONObject network = data.getJSONObject("network");
			JSONObject storange = data.getJSONObject("storange");
			String cod = clienSusbs.getString("cod");
			String clientId = clienSusbs.getString("clientId");
			String passClientId = clienSusbs.getString("password");
			String tenant = clienSusbs.getString("tenant");
			String nResource = resource.getString("name");
			String nNetwork = network.getString("name");
			String maskNet = network.getString("mask");
			String publiNet = network.getString("publicIp");
			String nStorange = storange.getString("name");
			String vm = storange.getString("VM");
			String location = storange.getString("location");
			System.out.println(vm);
			String ruta = Config.pathFile+nResource+".tf";
			File archivo = new File(ruta);
			BufferedWriter bw;
			if(archivo.exists()) {
				bw = new BufferedWriter(new FileWriter(archivo));
			    bw.write("# Configure the Microsoft Azure Provider\r\n" + 
			    		"provider \"azurerm\" {\r\n" + 
			    		"    subscription_id = \""+cod+"\"\r\n" + 
			    		"    client_id       = \""+clientId+"\"\r\n" + 
			    		"    client_secret   = \""+passClientId+"\"\r\n" + 
			    		"    tenant_id       = \""+tenant+"\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create a resource group \r\n" + 
			    		"resource \"azurerm_resource_group\" \"helloterraform\" {\r\n" + 
			    		"    name = \""+nResource+"\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create virtual network\r\n" + 
			    		"resource \"azurerm_virtual_network\" \"helloterraformnetwork\" {\r\n" + 
			    		"    name = \""+nNetwork+"\"\r\n" + 
			    		"    address_space = [\"10.0.0.0/16\"]\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create subnet\r\n" + 
			    		"resource \"azurerm_subnet\" \"helloterraformsubnet\" {\r\n" + 
			    		"    name = \""+maskNet+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    virtual_network_name = \"${azurerm_virtual_network.helloterraformnetwork.name}\"\r\n" + 
			    		"    address_prefix = \"10.0.2.0/24\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"	\r\n" + 
			    		"# create public IPs\r\n" + 
			    		"resource \"azurerm_public_ip\" \"helloterraformips\" {\r\n" + 
			    		"    name = \""+publiNet+"\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    public_ip_address_allocation = \"dynamic\"\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"TerraformDemo\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create network interface para Ubuntu\r\n" + 
			    		"resource \"azurerm_network_interface\" \"helloterraformnic\" {\r\n" + 
			    		"    name = \"tfni\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"\r\n" + 
			    		"    ip_configuration {\r\n" + 
			    		"        name = \"testconfiguration1\"\r\n" + 
			    		"        subnet_id = \"${azurerm_subnet.helloterraformsubnet.id}\"\r\n" + 
			    		"        private_ip_address_allocation = \"static\"\r\n" + 
			    		"        private_ip_address = \"10.0.2.5\"\r\n" + 
			    		"        public_ip_address_id = \"${azurerm_public_ip.helloterraformips.id}\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"\r\n" + 
			    		"\r\n" + 
			    		"# create storage account\r\n" + 
			    		"resource \"azurerm_storage_account\" \"hellotformstorage\" {\r\n" + 
			    		"    name = \""+nStorange+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    account_replication_type = \"LRS\"\r\n" + 
			    		"    account_tier = \"Standard\"\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"staging\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create storage container\r\n" + 
			    		"resource \"azurerm_storage_container\" \"helloterraformstoragestoragecontainer\" {\r\n" + 
			    		"    name = \"vhd\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    storage_account_name = \"${azurerm_storage_account.hellotformstorage.name}\"\r\n" + 
			    		"    container_access_type = \"private\"\r\n" + 
			    		"    depends_on = [\"azurerm_storage_account.hellotformstorage\"]\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create virtual machine Ubuntu\r\n" + 
			    		"resource \"azurerm_virtual_machine\" \"helloterraformvm\" {\r\n" + 
			    		"    name = \"terraformvm\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    network_interface_ids = [\"${azurerm_network_interface.helloterraformnic.id}\"]\r\n" + 
			    		"    vm_size = \"Standard_A0\"\r\n" + 
			    		"\r\n" + 
			    		"    storage_image_reference {\r\n" + 
			    		"        publisher = \"Canonical\"\r\n" + 
			    		"        offer = \"UbuntuServer\"\r\n" + 
			    		"        sku = \"14.04.2-LTS\"\r\n" + 
			    		"        version = \"latest\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    storage_os_disk {\r\n" + 
			    		"        name = \"myosdisk\"\r\n" + 
			    		"        vhd_uri = \"${azurerm_storage_account.hellotformstorage.primary_blob_endpoint}${azurerm_storage_container.helloterraformstoragestoragecontainer.name}/myosdisk.vhd\"\r\n" + 
			    		"        caching = \"ReadWrite\"\r\n" + 
			    		"        create_option = \"FromImage\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    os_profile {\r\n" + 
			    		"        computer_name = \"hostname\"\r\n" + 
			    		"        admin_username = \"carlos\"\r\n" + 
			    		"        admin_password = \"Password1234!\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    os_profile_linux_config {\r\n" + 
			    		"        disable_password_authentication = false\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"staging\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"");
			} else {
				bw = new BufferedWriter(new FileWriter(archivo));
				bw.write("# Configure the Microsoft Azure Provider\r\n" + 
			    		"provider \"azurerm\" {\r\n" + 
			    		"    subscription_id = \""+cod+"\"\r\n" + 
			    		"    client_id       = \""+clientId+"\"\r\n" + 
			    		"    client_secret   = \""+passClientId+"\"\r\n" + 
			    		"    tenant_id       = \""+tenant+"\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create a resource group \r\n" + 
			    		"resource \"azurerm_resource_group\" \"helloterraform\" {\r\n" + 
			    		"    name = \""+nResource+"\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create virtual network\r\n" + 
			    		"resource \"azurerm_virtual_network\" \"helloterraformnetwork\" {\r\n" + 
			    		"    name = \""+nNetwork+"\"\r\n" + 
			    		"    address_space = [\"10.0.0.0/16\"]\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create subnet\r\n" + 
			    		"resource \"azurerm_subnet\" \"helloterraformsubnet\" {\r\n" + 
			    		"    name = \""+maskNet+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    virtual_network_name = \"${azurerm_virtual_network.helloterraformnetwork.name}\"\r\n" + 
			    		"    address_prefix = \"10.0.2.0/24\"\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"	\r\n" + 
			    		"# create public IPs\r\n" + 
			    		"resource \"azurerm_public_ip\" \"helloterraformips\" {\r\n" + 
			    		"    name = \""+publiNet+"\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    public_ip_address_allocation = \"dynamic\"\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"TerraformDemo\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create network interface para Ubuntu\r\n" + 
			    		"resource \"azurerm_network_interface\" \"helloterraformnic\" {\r\n" + 
			    		"    name = \"tfni\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"\r\n" + 
			    		"    ip_configuration {\r\n" + 
			    		"        name = \"testconfiguration1\"\r\n" + 
			    		"        subnet_id = \"${azurerm_subnet.helloterraformsubnet.id}\"\r\n" + 
			    		"        private_ip_address_allocation = \"static\"\r\n" + 
			    		"        private_ip_address = \"10.0.2.5\"\r\n" + 
			    		"        public_ip_address_id = \"${azurerm_public_ip.helloterraformips.id}\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"\r\n" + 
			    		"\r\n" + 
			    		"# create storage account\r\n" + 
			    		"resource \"azurerm_storage_account\" \"hellotformstorage\" {\r\n" + 
			    		"    name = \""+nStorange+"\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    account_replication_type = \"LRS\"\r\n" + 
			    		"    account_tier = \"Standard\"\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"staging\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create storage container\r\n" + 
			    		"resource \"azurerm_storage_container\" \"helloterraformstoragestoragecontainer\" {\r\n" + 
			    		"    name = \"vhd\"\r\n" + 
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    storage_account_name = \"${azurerm_storage_account.hellotformstorage.name}\"\r\n" + 
			    		"    container_access_type = \"private\"\r\n" + 
			    		"    depends_on = [\"azurerm_storage_account.hellotformstorage\"]\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"# create virtual machine Ubuntu\r\n" + 
			    		"resource \"azurerm_virtual_machine\" \"helloterraformvm\" {\r\n" + 
			    		"    name = \"terraformvm\"\r\n" + 
			    		"    location = \""+location+"\"\r\n" +
			    		"    resource_group_name = \"${azurerm_resource_group.helloterraform.name}\"\r\n" + 
			    		"    network_interface_ids = [\"${azurerm_network_interface.helloterraformnic.id}\"]\r\n" + 
			    		"    vm_size = \"Standard_A0\"\r\n" + 
			    		"\r\n" + 
			    		"    storage_image_reference {\r\n" + 
			    		"        publisher = \"Canonical\"\r\n" + 
			    		"        offer = \"UbuntuServer\"\r\n" + 
			    		"        sku = \"14.04.2-LTS\"\r\n" + 
			    		"        version = \"latest\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    storage_os_disk {\r\n" + 
			    		"        name = \"myosdisk\"\r\n" + 
			    		"        vhd_uri = \"${azurerm_storage_account.hellotformstorage.primary_blob_endpoint}${azurerm_storage_container.helloterraformstoragestoragecontainer.name}/myosdisk.vhd\"\r\n" + 
			    		"        caching = \"ReadWrite\"\r\n" + 
			    		"        create_option = \"FromImage\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    os_profile {\r\n" + 
			    		"        computer_name = \"hostname\"\r\n" + 
			    		"        admin_username = \"carlos\"\r\n" + 
			    		"        admin_password = \"Password1234!\"\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    os_profile_linux_config {\r\n" + 
			    		"        disable_password_authentication = false\r\n" + 
			    		"    }\r\n" + 
			    		"\r\n" + 
			    		"    tags {\r\n" + 
			    		"        environment = \"staging\"\r\n" + 
			    		"    }\r\n" + 
			    		"}\r\n" + 
			    		"\r\n" + 
			    		"");
			}
			bw.close();			
			
	    	String comando = "cmd /c cd "+Config.pathFile+" && terraform init && terraform plan && terraform apply -auto-approve ";
	    	try {  
	            Process p = Runtime.getRuntime().exec(comando);  
	            BufferedReader in = new BufferedReader(  
	                                new InputStreamReader(p.getInputStream()));  
	            String line = null;  
	            while ((line = in.readLine()) != null) {  
	                System.out.println(line);
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }		    
		    File f1 = new File(Config.pathTerraform);
		    File f2 = new File(Config.pathTerraformConfig);
		    File f3 = new File(ruta);
		    borrarDirectorio(f1);
		    borrarDirectorio(f2);
		    borrarDirectorio(f3);
			return Response.ok().entity(data.toString()).build();
			
		}catch (Exception e) {
			e.printStackTrace();
			JSONObject msm = new JSONObject();
			msm.put("error", e.getMessage());
			return Response.serverError().entity(json.toString()).build();
		}
	}
	
	public void borrarDirectorio (File directorio){
		 File[] ficheros = directorio.listFiles();
		 
		 for (int x=0;x<ficheros.length;x++){
			 if (ficheros[x].isDirectory()) {
				  borrarDirectorio(ficheros[x]);
				}
				ficheros[x].delete();
		}
	}
	
	@POST
	@Path("/matrix")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMatriz(String info) {
		JSONObject data = null;
		try {
			data = new JSONObject(info);
			String entityid = data.getString("entityid");
			String prev = data.getString("prev");
			String actual = data.getString("actual");
			return Response.ok().entity(data.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("error", e.getMessage());
			return Response.serverError().entity(json.toString()).build();
		}
	}

	/*
	 * Crea la respuesta al cliente
	 */
	private Response request(String json, String type, String path) {
		Response response;
		String result = "";
		try {
			path = path.replace("+", "/").replace("%2B", "/").replace("%5B", "[").replace("%5D", "]");
			response = Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.serverError().entity(e.getMessage()).build();
		}
		return response;
	}
}