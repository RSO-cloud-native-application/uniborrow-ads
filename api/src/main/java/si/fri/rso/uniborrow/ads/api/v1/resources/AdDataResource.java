package si.fri.rso.uniborrow.ads.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.uniborrow.ads.models.entities.Ad;
import si.fri.rso.uniborrow.ads.models.entities.TargetAudience;
import si.fri.rso.uniborrow.ads.services.beans.AdDataProviderBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public Response getRandomAd() {
        Ad ad = adDataProviderBean.getRandomAd();
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @GET
    @Path("/{audience}")
    public Response getRandomTargetedAd(@PathParam("audience") TargetAudience targetAudience) {
        Ad ad = adDataProviderBean.getRandomTargetedAd(targetAudience);
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @GET
    @Path("/all")
    public Response getAll() {
        List<Ad> ad = adDataProviderBean.getAds();
        return Response.status(Response.Status.OK).entity(ad).build();
    }

    @POST
    @Path("/")
    public Response createAdd(Ad ad) {
        if (ad.getImageUrl() != null && ad.getUrl() != null && ad.getTargetAudience() != null) {
            ad = adDataProviderBean.addAd(ad);
        }
        if (ad == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(ad).build();
    }

}