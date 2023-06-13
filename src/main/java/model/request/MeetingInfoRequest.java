package model.request;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingInfoRequest {
	private Integer id;
	private Integer idCompany;
	private String nameMeeting;
	private Integer numberOrganized;
	private Integer yearOrganized;
	private Integer status;
	private String imageBanner;
	private Timestamp startTime;
	private Timestamp endTime;
	private String address;
	private String description;
}
