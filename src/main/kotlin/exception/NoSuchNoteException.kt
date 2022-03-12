package exception

import java.lang.RuntimeException

class NoSuchNoteException(message:String): RuntimeException(message)  {
}