all: sheepdog/sim/Sheepdog.class sheepdog/g9/Player.class

sheepdog/sim/Sheepdog.class: sheepdog/sim/*.java
	javac $^

sheepdog/g9/Player.class: sheepdog/g9/*.java
	javac $^

.PHONY: run
run: all
	java -ea sheepdog.sim.Sheepdog g9 10 2000 100 false true

.PHONY: clean
clean:
	$(RM) ./*~
	$(RM) sheepdog/sim/*.class
	$(RM) sheepdog/sim/*~
	$(RM) sheepdog/g9/*.class
	$(RM) sheepdog/g9/*~
