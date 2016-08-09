package il.org.spartan.refactoring.utils;

import java.util.*;
import org.eclipse.jdt.core.dom.*;

/** Enhances {@link ASTVisitor} with information on variable definition and
 * binding. To use, extend as usual, except that the {@link #preVisit(ASTNode)}
 * function is now <code><b>final</b></code>. If you must, override
 * {@link #preBindingVisit()}
 * @author Yossi Gil
 * @since 2016-08-08 18:11:23 +0300 */
public abstract class VariableBindingVisitor extends ASTVisitor {
  /** This function listens to the visiting actions and intercepts the calls as
   * necessary. At the first call to it during an invocation of
   * {@link ASTNode#accept(ASTVisitor)} on this object, it conducts a
   * preliminary full visit of the tree, starting from its root, collecting the
   * binding information.
   * @see org.eclipse.jdt.core.dom.ASTVisitor#preVisit(org.eclipse.jdt.core.dom.ASTNode) */
  @Override public final void preVisit(final ASTNode ¢) {
    if (!seen(¢))
      collect(¢);
    preVisit0(¢);
  }
  /** collects all binding information, starting at the {@link ASTNode} of the
   * root of the parameter
   * @param ¢ a node on the tree, whose root is the strarting point of the
   *          collection */
  void collect(ASTNode ¢) {
    // TBD
  }
  /** stores binding information of all nodes */
  final Map<ASTNode, Binding> environmentOf = new HashMap<>();
  boolean seen(ASTNode ¢) {
    return environmentOf.containsKey(¢);
  }

  public class Binding {
    // To be written
  }
  /** replaces {@link #preVisit(ASTNode)}
   * @param n JD */
  public void preVisit0(ASTNode n) {
    // To be realized by user
  }
}