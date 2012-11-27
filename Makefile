product:
	javac -cp . ec/app/product/Product.java

run_product:
	java ec.Evolve -file ec/app/product/product.params

squares:
	javac -cp . ec/app/squares/Squares.java

run_squares: squares
	java ec.Evolve -file ec/app/squares/squares.params
