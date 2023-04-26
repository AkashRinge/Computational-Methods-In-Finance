# Computational-Methods-In-Finance-Using-JAVA

1. Please make sure you have Java (jdk 16) installed and added to PATH in environment variables
2. Open cmd and go to the folder where jar is location
3. Run the command java --add-opens java.base/java.lang=ALL-UNNAMED -jar computational-finance-server.jar
4. Alternative - Install Eclipse -> Import Maven project -> Run with vm args: --add-opens java.base/java.lang=ALL-UNNAMED

### Project 1 - Random Number Simulation Methodologies
Random Number Generation, Random Variable Simulation. Inverse Transformation, Binomial Distribution, Box-Muller and Polar-Marsaglia methods.

### Project 2 - Monte Carlo Simulation and Numerical Integration Techniques
Functionality for solving a variety of mathematical problems. The project utilizes various APIs, to perform tasks such as plotting graphs, calculating option values, and simulating Monte Carlo (Antithetic) methods.

### Project 3 - Monte Carlo Simulation continued, Ito Processes, and Call option simulation
Simulating expected values and probabilities of certain 1-d and 2-d Ito processes. Monte Carlo simulations to estimate European call option prices, using both standard and variance reduction techniques. Price European call options using a 2-factor stochastic volatility model, and comparing different numerical methods. Generating and comparing pseudo-random and quasi-Monte Carlo sequences, and using them to estimate a specific integral.