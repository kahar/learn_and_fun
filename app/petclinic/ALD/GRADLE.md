# Context
I want to develop my skills with docker and microservices. Smaller projects are simple and I wanted something more complex - and I do not want build whole project from the bottom. This is why I choose to rewrite project spring petclinic with microservices. 

# Decision
Status: Accepted
Decision: Use gradle instead of maven

# Consequences
- Some plugins from maven are unavailable for gradle. 
- Some libraries are outdated. 
- Some scripts should be rewritten.

# Other options
- Maven - the temptation to just copy instead of configure would be big :)
- Other building tools - it would require learning new tools from scratch, which would prevent the project from being completed in a reasonable time
