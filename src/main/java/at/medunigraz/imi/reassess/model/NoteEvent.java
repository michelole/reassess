package at.medunigraz.imi.reassess.model;

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

	public Integer getSubjectId() {
		return subjectId;
	}
}
