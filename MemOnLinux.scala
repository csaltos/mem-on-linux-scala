object MemOnLinux {

  def getMemInfo() = {
    import java.io._
    val p = Runtime.getRuntime().exec("grep MemTotal /proc/meminfo")
    val i = new BufferedReader(new InputStreamReader(p.getInputStream()))
    val l = i.readLine()
    if (l != null) {
      "\\b([0-9]+)\\b.*[kK][bB]".r.findFirstMatchIn(l) match {
        case Some(result) =>
          if (result.groupCount > 0) {
            val totalKb = result.group(1).toLong
            val totalBytes = totalKb * 1024
            val totalMegas = totalKb / 1024
            val totalGigas = totalMegas / 1024
            s"${totalBytes} bytes, ${totalKb} kb, ${totalMegas} mb, ${totalGigas} gb"
          } else {
            "unknown"
          }
        case None =>
          "unknown"
      }
    } else {
      "unknown"
    }
  }

}
