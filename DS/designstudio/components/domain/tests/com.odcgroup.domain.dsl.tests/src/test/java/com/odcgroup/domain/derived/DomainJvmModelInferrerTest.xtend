package com.odcgroup.domain.derived

import com.google.inject.Inject
import com.odcgroup.mdf.ecore.MdfDomainImpl
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import com.odcgroup.domain.DomainInjectorProvider

@RunWith(XtextRunner)
@InjectWith(DomainInjectorProvider)
class DomainJvmModelInferrerTest {

	@Inject extension JvmModelGenerator

	@Inject extension ValidationTestHelper

	@Inject extension ParseHelper<MdfDomainImpl>

	@Test
	def void inferMdfClass() {
		'''
			Domain Foo
				@java:Package (value=test)
			namespace "http://www.odcgroup.com/foo"
			metamodelVersion 4.3.0.20110527
			Classes {
				/* class Foo */
				Foo {
					id : mml:string PK
					/* attribute of type short */
					attributeFoo : short
					/* association of type Foo */
					associationFoo -> Foo:Foo
					reverse {
						/* reverse association of type Foo */ 
						reverseAssociationFoo <- Foo:Foo:associationFoo
					}
				}
				/* class Bar which extends class Foo */
				Bar extends Foo:Foo {}
			}
		'''.generateJvmModel(
			'''
			=== DEFAULT_OUTPUTFoo/Bar.java ===
			package Foo;
			
			import Foo.Foo;
			
			/**
			 * class Bar which extends class Foo
			 */
			@SuppressWarnings("all")
			public class Bar extends Foo {
			}
			=== DEFAULT_OUTPUTFoo/Foo.java ===
			package Foo;
			
			import java.math.BigDecimal;
			
			/**
			 * class Foo
			 */
			@SuppressWarnings("all")
			public class Foo {
			  public String id;
			  
			  /**
			   * attribute of type short
			   */
			  public BigDecimal attributeFoo;
			  
			  /**
			   * association of type Foo
			   */
			  public Foo associationFoo;
			  
			  /**
			   * reverse association of type Foo
			   */
			  public Foo reverseAssociationFoo;
			}
			''')
	}

	@Test
	def void inferMdfDataset() {
		'''
			Domain Foo
				@java:Package (value=test)
			namespace "http://www.odcgroup.com/foo"
			metamodelVersion 4.3.0.20110527
			Classes {
				Foo {
					id : mml:string PK required
					attributeFoo : URI
				}
			}
			Datasets {
				/* dataset Bar */
				Bar basedOn Foo:Foo {
					/* derived property of type Foo */
					deriverdPropertyFoo : Foo:Foo
					/* property mapped to property attributeFoo of base type Bar */
					mappedPropertyFoo -> attributeFoo
					/* mapped property of type Bar */
					otherMappedPropertyFoo -> lalalaProperty [Foo:Bar]
				}
			}
		'''.generateJvmModel(
			'''
			=== DEFAULT_OUTPUTFoo/Bar.java ===
			package Foo;

			import Foo.Foo;
			
			/**
			 * dataset Bar
			 */
			@SuppressWarnings("all")
			public class Bar {
			  /**
			   * derived property of type Foo
			   */
			  public Foo deriverdPropertyFoo;
			  
			  /**
			   * property mapped to property attributeFoo of base type Bar
			   */
			  public String mappedPropertyFoo;
			  
			  /**
			   * mapped property of type Bar
			   */
			  public Bar otherMappedPropertyFoo;
			}
			=== DEFAULT_OUTPUTFoo/Foo.java ===
			package Foo;
			
			@SuppressWarnings("all")
			public class Foo {
			  public String id;
			  
			  public String attributeFoo;
			}
			''')
	}

	@Test
	def void inferMdfEnumeration() {
		'''
			Domain Foo
				@java:Package (value=test)
			namespace "http://www.odcgroup.com/foo"
			metamodelVersion 4.3.0.20110527
			Enumerations {
				/* enumeration Foo */
				Foo : mml:integer
					/* enum literal literalFoo */
					literalFoo = 1
					/* enum literal literalFoo2 */
					literalFoo2 = 2
					/* enum literal literalFoo3 */
					literalFoo3 = 3
				/* enumeration Switch */
				Switch : boolean
					/* enum literal on */
					on = true
					/* enum literal off */
					off = false
			}
		'''.generateJvmModel(
			'''
				=== DEFAULT_OUTPUTFoo/Foo.java ===
				package Foo;
				
				/**
				 * enumeration Foo
				 */
				@SuppressWarnings("all")
				public enum Foo {
				  /**
				   * enum literal literalFoo
				   */
				  literalFoo,
				  
				  /**
				   * enum literal literalFoo2
				   */
				  literalFoo2,
				  
				  /**
				   * enum literal literalFoo3
				   */
				  literalFoo3;
				}
				=== DEFAULT_OUTPUTFoo/Switch.java ===
				package Foo;
				
				/**
				 * enumeration Switch
				 */
				@SuppressWarnings("all")
				public enum Switch {
				  /**
				   * enum literal on
				   */
				  on,
				  
				  /**
				   * enum literal off
				   */
				  off;
				}
			''')
	}

	@Test
	def void inferMdfBusinessType() {
		'''
			Domain Foo
				@java:Package (value=test)
			namespace "http://www.odcgroup.com/foo"
			metamodelVersion 4.3.0.20110527
			BusinessTypes {
				/* business type Foo */
				Foo : dateTime
				/* business type Bar */
				Bar : mml:string
			}
		'''.generateJvmModel(
			'''
				=== DEFAULT_OUTPUTFoo/Bar.java ===
				package Foo;
				
				@SuppressWarnings("all")
				public class Bar {
				}
				=== DEFAULT_OUTPUTFoo/Foo.java ===
				package Foo;
				
				@SuppressWarnings("all")
				public class Foo {
				}
			''')
	}

	def void generateJvmModel(CharSequence domainCode, String expectedJavaCode) {
		val domain = domainCode.parse
		domain.assertNoErrors
		domain.assertNoIssues
		val fsa = new InMemoryFileSystemAccess
		domain.eResource.doGenerate(fsa)
		Assert.assertEquals(expectedJavaCode,
			'''
				«FOR file : fsa.allFiles.entrySet»
					=== «file.key» ===
					«file.value»
					«ENDFOR»
			'''.toString)

	}

}
