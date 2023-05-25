package model.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ResultElection {
	private Integer id;
	private String idCandidate;
	private String idShareholder;
	private Integer numberSharesForCandidate;
	private Timestamp timeElection;
}
