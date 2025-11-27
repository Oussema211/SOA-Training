package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/unites")
@Produces(MediaType.APPLICATION_JSON)
public class UniteEnseignementRessource {

    private UniteEnseignementBusiness business = new UniteEnseignementBusiness();

    // 1. Ajouter une UE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterUE(UniteEnseignement ue) {
        boolean ajoute = business.addUniteEnseignement(ue);
        if (ajoute) {
            return Response.status(201).entity(ue).build(); // 201 Created
        }
        return Response.status(400).build();
    }

    // 2. Lister toutes les UE
    @GET
    public List<UniteEnseignement> getAllUE() {
        return business.getListeUE();
    }

    // 3. Lister les UE par semestre → /api/unites/semestre?semestre=1
    @GET
    @Path("/semestre")
    public List<UniteEnseignement> getUEParSemestre(@QueryParam("semestre") int semestre) {
        return business.getUEBySemestre(semestre);
    }

    // 4. Supprimer une UE
    @DELETE
    @Path("/{code}")
    public Response supprimerUE(@PathParam("code") int code) {
        boolean supprime = business.deleteUniteEnseignement(code);
        if (supprime) {
            return Response.ok().build();
        }
        return Response.status(404).build();
    }

    // 5. Modifier une UE
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierUE(@PathParam("code") int code, UniteEnseignement ueModifiee) {
        ueModifiee.setCode(code); // Important : on force le code
        boolean modifie = business.updateUniteEnseignement(code, ueModifiee);
        if (modifie) {
            return Response.ok().build();
        }
        return Response.status(404).build();
    }

    // 6. Récupérer une UE par code → /api/unites/1
    @GET
    @Path("/{code}")
    public Response getUEParCode(@PathParam("code") int code) {
        UniteEnseignement ue = business.getUEByCode(code);
        if (ue != null) {
            return Response.ok(ue).build();
        }
        return Response.status(404).build();
    }
}