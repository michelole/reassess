package at.medunigraz.imi.reassess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "noteevents", schema = "mimiciii")
public class NoteEvent {
	@Id
	@Column(name = "row_id")
	private Integer rowId;

	@Column(name = "subject_id")
	private Integer subjectId;

	@Column(name = "hadm_id")
	private Integer hospitalAdmissionId;

	@Column(name = "chartdate")
	private Date chartDate;

	@Column(name = "charttime")
	private Date chartTime;

	@Column(name = "storetime")
	private Date storeTime;

	@Column(name = "category")
	private String category;

	@Column(name = "description")
	private String description;

	@Column(name = "cgid")
	private Integer caregiverId;

	@Column(name = "iserror")
	private Character isError;

	@Column(name = "text")
	private String text;

	public Integer getRowId() {
		return rowId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public Integer getHospitalAdmissionId() {
		return hospitalAdmissionId;
	}

	public Date getChartDate() {
		return chartDate;
	}

	public Date getChartTime() {
		return chartTime;
	}

	public Date getStoreTime() {
		return storeTime;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public Integer getCaregiverId() {
		return caregiverId;
	}

	public boolean isError() {
		return isError != null ? true : false;
	}

	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return "NoteEvent [rowId=" + rowId + ", subjectId=" + subjectId + ", hospitalAdmissionId=" + hospitalAdmissionId
				+ ", chartDate=" + chartDate + ", chartTime=" + chartTime + ", storeTime=" + storeTime + ", category="
				+ category + ", description=" + description + ", caregiverId=" + caregiverId + ", isError=" + isError
				+ ", text=" + text.substring(0, 51) + "(...) ]";
	}
}
