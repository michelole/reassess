package at.medunigraz.imi.reassess.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import at.medunigraz.imi.reassess.conceptmapper.metamap.MetaMapLiteFacade;

@Entity
@Table(name = "noteevents", schema = "mimiciii")
public class NoteEvent {
	
	@Id
	@Column(name = "row_id")
	private Integer rowId;

	@Column(name = "subject_id")
	private Integer subjectId;

	@Column(name = "hadm_id")
	private Integer admissionId;

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
	
	private static final String DISCHARGE_SUMMARY = "Discharge summary";
	
	public List<String> getCUIs() {
		return MetaMapLiteFacade.getInstance().map(getText());
	}
	
	public Set<String> getUniqueCUIs() {		
		return MetaMapLiteFacade.getInstance().uniqueMap(getText());
	}
	
	public String getAnnotatedText() {
		return MetaMapLiteFacade.getInstance().annotate(getText());
	}
	
	public boolean isDischargeSummary() {
		if (getCategory().equals(DISCHARGE_SUMMARY)) {
			return true;
		}
		
		return false;
	}

	public Integer getRowId() {
		return rowId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public Integer getAdmissionId() {
		return admissionId;
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
		return "NoteEvent [rowId=" + rowId + ", subjectId=" + subjectId + ", hospitalAdmissionId=" + admissionId
				+ ", chartDate=" + chartDate + ", chartTime=" + chartTime + ", storeTime=" + storeTime + ", category="
				+ category + ", description=" + description + ", caregiverId=" + caregiverId + ", isError=" + isError
				+ ", text=" + text.substring(0, 51) + "(...) ]";
	}
}
