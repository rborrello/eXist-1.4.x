xquery version "1.0";

import module namespace const = "http://xproc.net/xproc/const";
import module namespace xproc = "http://xproc.net/xproc";
import module namespace u = "http://xproc.net/xproc/util";

declare variable $pipeline external;
declare variable $stdin    external;
declare variable $debug    external;

xproc:run(doc($pipeline), doc($stdin), $debug)



