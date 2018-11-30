package cucumber.eclipse.steps.jdt;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import cucumber.api.java.ObjectFactory;
import cucumber.eclipse.steps.integration.IStepGenerator;
import cucumber.runtime.ClassFinder;

public class StepGenerator implements IStepGenerator {
	
	@Override
	public boolean supports(IFile file) {
		return file.getName().endsWith(".java");
	}

	@Override
	public TextEdit createStepSnippet(gherkin.formatter.model.Step step, IDocument targetDocument)
			throws IOException, CoreException {
//		TypeRegistry typeRegistry = null;
//		Backend backend = new JavaBackend(new EmptyObjectFactory(), new EmptyClassFinder(), typeRegistry);
//		//FIXME we must upgrade jerkins parser!
//		List<String> snippetTexts = backend.getSnippet(null, step.getKeyword(), new FunctionNameGenerator(new CamelCaseConcatenator()));
//		for (String snippetText : snippetTexts) {
//			ASTParser parser = ASTParser.newParser(AST.JLS3);
//			parser.setSource(targetDocument.get().toCharArray());
//			
//			CompilationUnit target = (CompilationUnit) parser.createAST(null);
//			target.recordModifications();
//			
//			TypeDeclaration targetType = (TypeDeclaration) target.types().get(0);
//			
//			parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
//			parser.setSource(snippetText.toCharArray());
//			TypeDeclaration fragmentContainer = (TypeDeclaration) parser.createAST(null);
//			
//			for (Object fragmentObject : fragmentContainer.bodyDeclarations()) {
//				BodyDeclaration fragmentNode = (BodyDeclaration) fragmentObject;
//			
//				// clone into target AST
//				fragmentNode = (BodyDeclaration) ASTNode.copySubtree(target.getAST(), fragmentNode);
//			
//				@SuppressWarnings("unchecked")
//				List<BodyDeclaration> bodyDeclarations = targetType.bodyDeclarations();
//				
//				bodyDeclarations.add(getInsertionPoint(targetType, fragmentNode), fragmentNode);
//			}
//			
//			return target.rewrite(targetDocument, null);
//		}
		return null;
	}
	
	private static int getInsertionPoint(TypeDeclaration source, BodyDeclaration fragment) {
		if (source.bodyDeclarations().isEmpty()) {
			return 0;
		}

		if (fragment.getNodeType() == ASTNode.METHOD_DECLARATION) {
			int last = getLastFragmentOfTypePosition(source, fragment.getNodeType(),
				fragment.getModifiers());

			if (last >= 0) {
				return last + 1;
			}

			int afterFields = getLastFragmentOfTypePosition(source, ASTNode.FIELD_DECLARATION);
			return (afterFields >= 0) ? afterFields + 1 : 0;
		}

		return 0;
	}

	private static int getLastFragmentOfTypePosition(TypeDeclaration source, int nodeType) {
		return getLastFragmentOfTypePosition(source, nodeType, -1);
	}

	private static int getLastFragmentOfTypePosition(TypeDeclaration source, int nodeType, int modifiers) {
		for (int i = source.bodyDeclarations().size() - 1; i >= 0; i --) {
			BodyDeclaration bodyDecl = (BodyDeclaration) source.bodyDeclarations().get(i);

			if (bodyDecl.getNodeType() == nodeType && (modifiers == -1 || bodyDecl.getModifiers() == modifiers)) {
				return i;
			}
		}

		return -1;
	}

	private static class EmptyObjectFactory implements ObjectFactory {
		@Override
		public void start() {
			// no action
		}
	
		@Override
		public void stop() {
			// no action
		}
	
		@Override
		public boolean addClass(Class<?> glueClass) {
			// no action
			return true;
		}
	
		@Override
		public <T> T getInstance(Class<T> glueClass) {
			return null;
		}
	}

	private static class EmptyClassFinder implements ClassFinder {
		@Override
		public <T> Collection<Class<? extends T>> getDescendants(Class<T> parentType, String packageName) {
			return new ArrayList<Class<? extends T>>();
		}

		@Override
		public <T> Class<? extends T> loadClass(String arg0) throws ClassNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}
	}


}
