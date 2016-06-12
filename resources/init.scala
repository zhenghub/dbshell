import org.freefeeling.DbShell._
import org.freefeeling.dbshell.Exceptions.DbShellInitialException
import scalikejdbc._

val args = System.getenv("DbShell_args").split("\\s+");
val realdb = initDb(args.drop(1));
implicit val session = realdb.borrow();
