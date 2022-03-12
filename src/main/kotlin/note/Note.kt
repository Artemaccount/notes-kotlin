package note

data class Note(val id: Int, val text:String, val title:String, var commentsList: MutableMap<Int, NoteComment> = mutableMapOf()) {

}