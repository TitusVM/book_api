package ch.hearc.book_api.model;

import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {

	private Long id;
	private String author;
	private String name;
	private String editor;
	private LocalDate release;

	public BookResponse(Book book) {
		this.id = book.getId();
		this.author = book.getAuthor();
		this.name = book.getName();
		this.editor = book.getEditor();
		this.release = book.getRelease();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookResponse [id=");
		builder.append(id);
		builder.append(", author=");
		builder.append(author);
		builder.append(", name=");
		builder.append(name);
		builder.append(", editor=");
		builder.append(editor);
		builder.append(", release=");
		builder.append(release);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, editor, id, name, release);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookResponse other = (BookResponse) obj;
		return Objects.equals(author, other.author) && Objects.equals(editor, other.editor)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(release, other.release);
	}
}
