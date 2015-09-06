all:
	make clean
	make build
	make run

run:
	@reset
	@echo "Running..."
	java -Djava.library.path=native -jar build/libs/eco.jar

make build:
	@echo "Starting build..."	
	gradle build

clean:
	@echo "Cleaning..."
	gradle clean
	@rm -rf saves
	@rm -rf savetxt
	@rm -rf screenshots
	@echo "Done"
