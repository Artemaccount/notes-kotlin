package note

import exception.CommentNotDeletedException
import exception.CommentsListIsEmptyException
import exception.NoSuchCommentException
import exception.NoSuchNoteException

object NoteService {
    var notes = mutableMapOf<Int, Note>()

    fun add(note: Note): Note? {
        return notes.put(note.id, note)
    }

    fun addCommentToNote(noteId: Int, comment: NoteComment) {
        notes[noteId] ?: throw NoSuchNoteException("Note is not found")
        notes[noteId]?.commentsList?.set(comment.id, comment)
    }

    fun delete(noteId: Int) {
        notes[noteId] ?: throw NoSuchNoteException("Note is not found")
        notes.remove(noteId)
    }

    fun deleteComment(noteId: Int, commentId: Int): Boolean {
        val note = notes[noteId] ?: throw NoSuchNoteException("Note is not found")
        val comment = note.commentsList[commentId]
        if (comment == null || comment.isDeleted) {
            throw NoSuchCommentException("Comment is not found")
        }
        notes[noteId]?.commentsList?.get(commentId)?.isDeleted = true
        return true
    }

    fun editNote(noteId: Int, newNote: Note): Boolean {
        notes[noteId] ?: throw NoSuchNoteException("Note is not found")
        notes[noteId] = newNote
        return true
    }

    fun get(): MutableMap<Int, Note> {
        return notes
    }

    fun getById(noteId: Int): Note? {
        notes[noteId] ?: throw NoSuchNoteException("Note is not found")
        return notes[noteId]
    }


    fun getComments(noteId: Int): MutableMap<Int, NoteComment> {
        val newCommentList = mutableMapOf<Int, NoteComment>()
        val commentsList = notes[noteId]?.commentsList ?: throw CommentsListIsEmptyException("Comments list is empty")

        for ((key, value) in commentsList) {
            if (!value.isDeleted) {
                newCommentList.put(key, value)
            }
        }
        return newCommentList
    }

    fun restoreComment(noteId: Int, commentId: Int) {
        notes[noteId] ?: throw NoSuchNoteException("Note not found")

        val noteComment =
            notes[noteId]?.commentsList?.get(commentId) ?: throw NoSuchCommentException("Comment is not found")

        if (!noteComment.isDeleted) {
            throw CommentNotDeletedException("Comment is not deleted")
        }
        notes[noteId]?.commentsList?.get(commentId)?.isDeleted = false
    }
}
