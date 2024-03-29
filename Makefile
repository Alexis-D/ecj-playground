CP=-cp '.:*'
RUN=java ${CP} ec.Evolve -file
product:
	javac ${CP} ec/app/product/*.java

run_product: product
	${RUN} ec/app/product/product.params

run_product_de: product
	${RUN} ec/app/product/product.de.params

squares:
	javac ${CP} ec/app/squares/Squares.java

run_squares: squares
	${RUN} ec/app/squares/squares.params

console:
	java ${CP} ec.display.Console

lib:
	javac ${CP} ec/stats/*.java ${CP} ec/custom/*.java

all: lib product squares
