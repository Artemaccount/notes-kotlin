package exception

import java.lang.RuntimeException

class NoSuchCommentException(message:String): RuntimeException(message)  {
}