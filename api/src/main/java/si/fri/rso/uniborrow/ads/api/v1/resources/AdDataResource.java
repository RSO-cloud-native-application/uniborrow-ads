package si.fri.rso.uniborrow.ads.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.uniborrow.ads.models.entities.Ad;
import si.fri.rso.uniborrow.ads.models.entities.TargetAudience;
import si.fri.rso.uniborrow.ads.services.beans.AdDataProviderBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log
@ApplicationScoped
@Path("/ads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdDataResource {

    @Inject
    private AdDataProviderBean adDataProviderBean;

    @GET
    @Path("/")
    @Operation(description = "Get random ad.", summary = "Get random ad.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Random ad.",
                    content = @Content(schema = @Schema(implementation = Ad.class))
            )
    })
    public Response getRandomAd() {
        Ad ad = adDataProviderBean.getRandomAd();
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @GET
    @Path("/{audience}")
    @Operation(description = "Get random ad for target audience.", summary = "Get random ad for target audience.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Random ad for target audience.",
                    content = @Content(schema = @Schema(implementation = Ad.class))
            )
    })
    public Response getRandomTargetedAd(
            @Parameter(
                    description = "Target audience.",
                    required = true
            )
            @PathParam("audience") TargetAudience targetAudience) {
        Ad ad = adDataProviderBean.getRandomTargetedAd(targetAudience);
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @POST
    @Path("/")
    @Operation(description = "Add a new ad to ads list.", summary = "Add a new ad to ads list.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Ad successfully added.",
                    content = @Content(schema = @Schema(implementation = Ad.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad request"
            )
    })
    public Response createAdd(
            @RequestBody(
                    description = "Ad definition.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Ad.class))
            )
                    Ad ad) {
        if (ad.getImageUrl() != null && ad.getUrl() != null && ad.getTargetAudience() != null) {
            ad = adDataProviderBean.addAd(ad);
        }
        if (ad == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @DELETE
    @Path("{adId}")
    @Operation(description = "Remove add from ads list.", summary = "Remove add from ads list.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Ad successfully removed.",
                    content = @Content(schema = @Schema(implementation = Ad.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Ad was not found."
            )
    })
    public Response deleteAd(@Parameter(
            description = "Target audience.",
            required = true
    ) @PathParam("adId") Integer adId) {
        boolean deleted = adDataProviderBean.deleteAd(adId);
        if (deleted) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}