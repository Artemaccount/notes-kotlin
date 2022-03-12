package exception

import java.lang.RuntimeException

class CommentNotDeletedException(message:String): RuntimeException(message) {
}