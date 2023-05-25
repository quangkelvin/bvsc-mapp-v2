package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import common.StatusCode;
import dao.CandidateDao;
import dao.daoimpl.CandidateDaoImpl;
import model.ApiResponse;
import model.entity.Candidate;
import model.request.CandidateRequest;

@Path("candidate")
public class CandidateService {
	private static final Logger logger = Logger.getLogger(CandidateService.class);
	
	@Inject
	private CandidateDao<Candidate> candidateDaoImpl;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response candidateDetails(@PathParam("id") String id) {
		logger.info("ID request: " + id);
		try {
			candidateDaoImpl = new CandidateDaoImpl();
			Optional<Candidate> opCandidate = candidateDaoImpl.getById(id);
			if (opCandidate.isEmpty()) {
				return Response.ok(new ApiResponse(
						StatusCode.DATA_FAILED.getValue(),
						StatusCode.DATA_FAILED.getDescription(),
						null)).build();
			}
			return Response.ok(new ApiResponse(
					StatusCode.DATA_SUCCESS.getValue(),
					StatusCode.DATA_SUCCESS.getDescription(),
					opCandidate.get())).build();

		} catch (Exception e) {
			logger.error("ERROR : "+e.getMessage());
			return Response.ok(new ApiResponse(
					StatusCode.DATA_FAILED.getValue(),
					StatusCode.DATA_FAILED.getDescription(),
					null)).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response candidates() {
		logger.info("Get All Candidate");
		try {
			
			List<Candidate> candidates = candidateDaoImpl.getAll();
			return Response.ok(
					new ApiResponse(
							StatusCode.DATA_SUCCESS.getValue(),
							StatusCode.DATA_SUCCESS.getDescription(), candidates))
					.build();
		} catch (Exception e) {
			return Response.ok(
					new ApiResponse(
							StatusCode.DATA_FAILED.getValue(),
							StatusCode.DATA_FAILED.getDescription(), null))
					.build();
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allByElection/{idElection}")
	public Response findAllByIdMeeting(@PathParam("idElection")String idElection) {
		List<Candidate> candidates = new ArrayList<>();
		try {
			candidates = candidateDaoImpl.findAllCandidateByIdElection(idElection);
			return Response.ok(
					new ApiResponse(
							StatusCode.DATA_SUCCESS.getValue(),
							StatusCode.DATA_SUCCESS.getDescription(), candidates))
					.build();
		} catch (Exception e) {
			return Response.ok(new ApiResponse(
					StatusCode.DATA_FAILED.getValue(),
					StatusCode.DATA_FAILED.getDescription(), null))
			.build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCandidate(CandidateRequest candidateReq) {
		Candidate candidate = new Candidate();
		try {
			candidate.setIdElection(candidateReq.getIdElection());
			candidate.setFullname(candidateReq.getFullname());
			candidate.setBirthday(candidateReq.getBirthday());
			candidate.setAddress(candidateReq.getAddress());
			candidate.setSummaryInfo(candidateReq.getSummaryInfo());
			int insert = candidateDaoImpl.save(candidate);
			if(insert==0) {
				return Response.ok(new ApiResponse(
						StatusCode.INSERT_FAILED.getValue(),
						StatusCode.INSERT_FAILED.getDescription(),
						null)).build();
			}
			return Response.ok(new ApiResponse(
					StatusCode.INSERT_SUCCESS.getValue(),
					StatusCode.INSERT_SUCCESS.getDescription(), 
					candidate)).build();
		} catch (Exception e) {
			logger.error("ERROR INSERT : "+e.getMessage());
			return Response.ok(new ApiResponse(
					StatusCode.INSERT_FAILED.getValue(),
					StatusCode.INSERT_FAILED.getDescription(),
					null)).build();
		}
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response editCandidate(@PathParam("id")Integer id,CandidateRequest candidateReq) {
		Candidate candidate = new Candidate();
		try {
			candidate.setId(id);
			candidate.setIdElection(candidateReq.getIdElection());
			candidate.setFullname(candidateReq.getFullname());
			candidate.setBirthday(candidateReq.getBirthday());
			candidate.setAddress(candidateReq.getAddress());
			candidate.setSummaryInfo(candidateReq.getSummaryInfo());
			int edit = candidateDaoImpl.update(candidate);
			if(edit==0) {
				return Response.ok(new ApiResponse(
						StatusCode.UPDATE_FAILED.getValue(),
						StatusCode.UPDATE_FAILED.getDescription(),
						null)).build();
			}
			return Response.ok(new ApiResponse(
					StatusCode.UPDATE_SUCCESS.getValue(),
					StatusCode.UPDATE_SUCCESS.getDescription(), 
					candidate)).build();
		} catch (Exception e) {
			logger.error("ERROR INSERT : "+e.getMessage());
			return Response.ok(new ApiResponse(
					StatusCode.UPDATE_FAILED.getValue(),
					StatusCode.UPDATE_FAILED.getDescription(),
					null)).build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response deleteCandidate(@PathParam("id")Integer id) {
		Candidate candidate = new Candidate();
		try {
			candidate.setId(id);
			int delete=candidateDaoImpl.delete(candidate);
			if(delete==0) {
				return Response.ok(new ApiResponse(
						StatusCode.DELETE_FAILED.getValue(),
						StatusCode.DELETE_FAILED.getDescription(),
						null)).build();
			}
			return Response.ok(new ApiResponse(
					StatusCode.DELETE_SUCCESS.getValue(),
					StatusCode.DELETE_SUCCESS.getDescription(),
					null)).build();
		} catch (Exception e) {
			logger.error("ERROR : "+e.getMessage());
			return Response.ok(new ApiResponse(
					StatusCode.DELETE_FAILED.getValue(),
					StatusCode.DELETE_FAILED.getDescription(),
					null)).build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete-by-id-election/{idElection}")
	public Response deleteCandidateByIdElection(@PathParam("idElection")String idElection) {
		Candidate candidate = new Candidate();
		try {
			candidate.setIdElection(idElection);
			int delete=candidateDaoImpl.deleteByIdElection(candidate);
			if(delete==0) {
				return Response.ok(new ApiResponse(
						StatusCode.DELETE_FAILED.getValue(),
						StatusCode.DELETE_FAILED.getDescription(),
						null)).build();
			}
			return Response.ok(new ApiResponse(
					StatusCode.DELETE_SUCCESS.getValue(),
					StatusCode.DELETE_SUCCESS.getDescription(),
					null)).build();
		} catch (Exception e) {
			logger.error("ERROR : "+e.getMessage());
			return Response.ok(new ApiResponse(
					StatusCode.DELETE_FAILED.getValue(),
					StatusCode.DELETE_FAILED.getDescription(),
					null)).build();
		}
	}
	
}