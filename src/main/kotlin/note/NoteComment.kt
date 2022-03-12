package note

data class NoteComment(val id: Int, val message:String, var isDeleted: Boolean = false)
