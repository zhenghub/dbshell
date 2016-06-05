val argsStr = System.getenv("DbShell_args");
println(s"dbshell args = ${argsStr}");

import ammonite.ops._;

val args = argsStr.split("\\s+");
val progHome = args(0);

(ls ! Path(progHome) / 'lib).foreach(load.cp);

load.exec(Path(progHome) /'bin / "init.scala");