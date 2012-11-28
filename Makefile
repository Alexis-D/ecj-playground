product:
	javac -cp . ec/app/product/Product.java

run_product: product
	java ec.Evolve -file ec/app/product/product.params

run_product_de: product
	java ec.Evolve -file ec/app/product/product.de.params

squares:
	javac -cp . ec/app/squares/Squares.java

run_squares: squares
	java ec.Evolve -file ec/app/squares/squares.params
