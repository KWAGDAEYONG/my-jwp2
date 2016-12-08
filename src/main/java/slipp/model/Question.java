package slipp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	@Column(length = 100, nullable = false)
	@JsonProperty
	private String title;

	@Column(nullable = false)
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private LocalDateTime createDate = LocalDateTime.now();
	
	@OneToMany(mappedBy="question")
	@Where(clause = "deleted = false") //where 조건절 추가 조건절
	@OrderBy("id ASC") //orderby
	private List<Answer> answers;
	
	private boolean deleted;

	public void setId(Long id) {
		this.id = id;
	}

	public void writeBy(User writer) {
		this.writer = writer;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getFormattedCreateDate() {
		if (createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
	
	public boolean isSameWriter(User loginUser) {
		System.out.println("writer : " + writer);
		return this.writer.equals(loginUser);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}
}
