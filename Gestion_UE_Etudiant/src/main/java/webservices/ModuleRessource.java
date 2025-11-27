package webservices;

import entities.Module;
import metiers.ModuleBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
public class ModuleRessource {

    private ModuleBusiness business = new ModuleBusiness();

    // Ajouter un module
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterModule(Module module) {
        boolean ajoute = business.addModule(module);
        if (ajoute) {
            return Response.status(201).entity(module).build();
        }
        return Response.status(400).entity("UE non trouvée").build();
    }

    // Lister tous les modules
    @GET
    public List<Module> getAllModules() {
        return business.getAllModules();
    }

    // Récupérer un module par matricule
    @GET
    @Path("/{matricule}")
    public Response getModule(@PathParam("matricule") String matricule) {
        Module m = business.getModuleByMatricule(matricule);
        if (m != null) {
            return Response.ok(m).build();
        }
        return Response.status(404).build();
    }

    // Supprimer un module
    @DELETE
    @Path("/{matricule}")
    public Response supprimerModule(@PathParam("matricule") String matricule) {
        boolean supprime = business.deleteModule(matricule);
        return supprime ? Response.ok().build() : Response.status(404).build();
    }

    // Modifier un module
    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierModule(@PathParam("matricule") String matricule, Module module) {
        module.setMatricule(matricule);
        boolean modifie = business.updateModule(matricule, module);
        return modifie ? Response.ok().build() : Response.status(404).build();
    }

    // Modules par UE → /api/modules/ue?code=1
    @GET
    @Path("/ue")
    public Response getModulesParUE(@QueryParam("code") int codeUE) {
        var ue = business.uniteEnseignementBusiness.getUEByCode(codeUE);
        if (ue == null) {
            return Response.status(404).entity("UE non trouvée").build();
        }
        List<Module> modules = business.getModulesByUE(ue);
        return Response.ok(modules).build();
    }
}