package il.org.spartan.spartanizer.ast.navigate;

import java.util.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.ast.safety.*;

/** A class to find all sort all things about a node, typically some small
 * analyses.
 * @author Ori Marcovitch
 * @since 2016 */
public enum find {
  ;
  public static Set<Name> dependencies(ASTNode n) {
    Set<Name> $ = new HashSet<>();
    n.accept(new ASTVisitor() {
      @Override public boolean visit(SimpleName node) {
        if (!izMethodName(node))
          $.add(node);
        return true;
      }

      boolean izMethodName(SimpleName ¢) {
        return iz.methodInvocation(step.parent(¢)) && step.name(az.methodInvocation(step.parent(¢))).equals(¢);
      }

      @Override public boolean visit(QualifiedName node) {
        $.add(node);
        return true;
      }
    });
    return $;
  }
}