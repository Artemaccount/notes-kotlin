package exception

import java.lang.RuntimeException

class CommentsListIsEmptyException(message:String): RuntimeException(message) {
}