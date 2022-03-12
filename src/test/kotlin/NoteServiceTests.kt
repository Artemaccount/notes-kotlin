import exception.NoSuchNoteException
import note.Note
import note.NoteComment
import note.NoteService
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

class NoteServiceTests {

    @Test
    fun add_success_test() {
        NoteService.get().clear()
        val newNote = Note(16, "some text", "some title")
        NoteService.add(newNote)
        val expectedSize = 1
        val actualSize = NoteService.get().size
        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun add_comment_to_note_success_test() {
        val newNote = Note(2, "some text", "some title")
        NoteService.add(newNote)
        val newComment = NoteComment(1, "дизлайк отписка")
        NoteService.addCommentToNote(2, newComment)
        val addedComment = NoteService.getById(2).commentsList[1]
        assertEquals(newComment, addedComment)
    }

    @Test(expected = NoSuchNoteException::class)
    fun add_comment_to_note_failure_test() {
        val newNote = Note(4, "some text", "some title")
        NoteService.add(newNote)
        val newComment = NoteComment(1, "дизлайк отписка")
        NoteService.addCommentToNote(5, newComment)
    }

    @Test(expected = NoSuchNoteException::class)
    fun delete_success_test() {
        val newNote = Note(2, "some text", "some title")
        NoteService.add(newNote)
        NoteService.delete(newNote.id)
        NoteService.getById(2)
    }

    @Test
    fun delete_comment_success_test() {
        val newNote = Note(22, "some text", "some title")
        NoteService.add(newNote)
        NoteService.add(newNote)
        val newComment = NoteComment(1, "дизлайк отписка")
        NoteService.addCommentToNote(22, newComment)
        NoteService.deleteComment(22, 1)
        NoteService.getById(22).commentsList[1]?.let { assertTrue(it.isDeleted) }
    }

    @Test
    fun edit_note_success_test() {
        val note = Note(6, "some text", "some title")
        NoteService.add(note)
        val newNote = Note(6, "other text", "other title")
        NoteService.editNote(6, newNote)
        val actualNote = NoteService.getById(6)
        assertEquals(newNote, actualNote)
    }

    @Test
    fun get_comments_success_test() {
        val note = Note(11, "some text", "some title")
        NoteService.add(note)
        val comment1 = NoteComment(1, "дизлайк отписка")
        val comment2 = NoteComment(2, "дизлайк отписка")
        val comment3 = NoteComment(3, "дизлайк отписка")
        val comment4 = NoteComment(4, "дизлайк отписка")
        NoteService.addCommentToNote(11, comment1)
        NoteService.addCommentToNote(11, comment2)
        NoteService.addCommentToNote(11, comment3)
        NoteService.addCommentToNote(11, comment4)

        val commentsMap = NoteService.getComments(11)
        assertEquals(commentsMap.size, 4)
    }

    @Test
    fun restore_comment_success_test() {
        val note = Note(14, "some text", "some title")
        NoteService.add(note)
        val comment1 = NoteComment(1, "дизлайк отписка")
        NoteService.addCommentToNote(14, comment1)
        val actualSizeAfterAddedComment = NoteService.getComments(14).size
        val expectedSizeAfterAddedComment = 1
        assertEquals(expectedSizeAfterAddedComment, actualSizeAfterAddedComment)

        NoteService.deleteComment(14, 1)

        val actualSizeAfterDeletingComment = NoteService.getComments(14).size
        val expectedSizeAfterDeletingComment = 0

        assertEquals(expectedSizeAfterDeletingComment, actualSizeAfterDeletingComment)

        NoteService.restoreComment(14, 1)

        val actualSizeAfterRestoreComment = NoteService.getComments(14).size
        val expetctedSizeAfterRestoreComment = 1

        assertEquals(expetctedSizeAfterRestoreComment, actualSizeAfterRestoreComment)

    }
}