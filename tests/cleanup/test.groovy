/*
 * A simple pipeline with some intermediate files to test
 * cleanup. Note that the last output (xls) depends only on the output
 * of the first stage (html), but not on the intermediate ones (csv, xls).
 * So we should be able to update the csv,xls files without actually requiring a 
 * rebuild of the last stage.
 */
@transform("html")
hello = {
	exec "cp $input $output"
        Thread.sleep(1100)
}

@transform("csv")
there = {
	exec "cp $input $output"
        Thread.sleep(1100)
}

@transform("xml")
world = {
	exec "cp $input $output"
        Thread.sleep(1100)
}

@transform("xls")
take = {
  from("html") {
    exec "cp $input $output"
    Thread.sleep(1100)
  }
}

@preserve
me = {
  exec "echo me > $output"
}

run {
	hello + there + world + take + me
}
