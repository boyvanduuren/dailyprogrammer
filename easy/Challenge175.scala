    object Challenge175 {
      def main(args: Array[String]) {
        var a = 0;
        while (scala.util.Random.shuffle(
            args(0).split("").toSeq).mkString("") != args(1)) {
          a += 1;
        }
        println(s"Sorting took $a iterations");
      }
    }
