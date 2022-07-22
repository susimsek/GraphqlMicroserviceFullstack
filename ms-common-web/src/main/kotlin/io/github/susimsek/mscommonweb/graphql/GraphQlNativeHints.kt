package io.github.susimsek.mscommonweb.graphql

import com.apollographql.federation.graphqljava.FederationDirectives
import com.apollographql.federation.graphqljava.FederationSdlPrinter
import graphql.ExceptionWhileDataFetching
import graphql.GraphQL
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.GraphqlErrorException
import graphql.InvalidSyntaxError
import graphql.SerializationError
import graphql.TypeMismatchError
import graphql.UnresolvedTypeError
import graphql.analysis.QueryVisitorFieldArgumentEnvironment
import graphql.analysis.QueryVisitorFieldArgumentInputValue
import graphql.execution.AbortExecutionException
import graphql.execution.Execution
import graphql.execution.InputMapDefinesTooManyFieldsException
import graphql.execution.MissingRootTypeException
import graphql.execution.NonNullableFieldWasNullError
import graphql.language.AbstractDescribedNode
import graphql.language.AbstractNode
import graphql.language.Argument
import graphql.language.ArrayValue
import graphql.language.BooleanValue
import graphql.language.Definition
import graphql.language.DescribedNode
import graphql.language.Directive
import graphql.language.DirectiveDefinition
import graphql.language.DirectiveLocation
import graphql.language.DirectivesContainer
import graphql.language.Document
import graphql.language.EnumTypeDefinition
import graphql.language.EnumTypeExtensionDefinition
import graphql.language.EnumValue
import graphql.language.EnumValueDefinition
import graphql.language.Field
import graphql.language.FieldDefinition
import graphql.language.FloatValue
import graphql.language.FragmentDefinition
import graphql.language.FragmentSpread
import graphql.language.ImplementingTypeDefinition
import graphql.language.InlineFragment
import graphql.language.InputObjectTypeDefinition
import graphql.language.InputObjectTypeExtensionDefinition
import graphql.language.InputValueDefinition
import graphql.language.IntValue
import graphql.language.InterfaceTypeDefinition
import graphql.language.InterfaceTypeExtensionDefinition
import graphql.language.ListType
import graphql.language.NamedNode
import graphql.language.Node
import graphql.language.NonNullType
import graphql.language.NullValue
import graphql.language.ObjectField
import graphql.language.ObjectTypeDefinition
import graphql.language.ObjectTypeExtensionDefinition
import graphql.language.ObjectValue
import graphql.language.OperationDefinition
import graphql.language.OperationTypeDefinition
import graphql.language.SDLDefinition
import graphql.language.SDLNamedDefinition
import graphql.language.ScalarTypeDefinition
import graphql.language.ScalarTypeExtensionDefinition
import graphql.language.ScalarValue
import graphql.language.SchemaDefinition
import graphql.language.SchemaExtensionDefinition
import graphql.language.Selection
import graphql.language.SelectionSet
import graphql.language.SelectionSetContainer
import graphql.language.StringValue
import graphql.language.Type
import graphql.language.TypeDefinition
import graphql.language.TypeName
import graphql.language.UnionTypeDefinition
import graphql.language.UnionTypeExtensionDefinition
import graphql.language.Value
import graphql.language.VariableDefinition
import graphql.language.VariableReference
import graphql.parser.ParserOptions
import graphql.relay.InvalidCursorException
import graphql.relay.InvalidPageSizeException
import graphql.scalar.GraphqlBooleanCoercing
import graphql.scalar.GraphqlFloatCoercing
import graphql.scalar.GraphqlIDCoercing
import graphql.scalar.GraphqlIntCoercing
import graphql.scalar.GraphqlStringCoercing
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLCodeRegistry
import graphql.schema.GraphQLDirective
import graphql.schema.GraphQLEnumType
import graphql.schema.GraphQLEnumValueDefinition
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLInputObjectField
import graphql.schema.GraphQLInputObjectType
import graphql.schema.GraphQLInterfaceType
import graphql.schema.GraphQLList
import graphql.schema.GraphQLNamedType
import graphql.schema.GraphQLNonNull
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLOutputType
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import graphql.schema.GraphQLSchemaElement
import graphql.schema.GraphQLUnionType
import graphql.schema.TypeResolver
import graphql.schema.TypeResolverProxy
import graphql.schema.idl.CombinedWiringFactory
import graphql.schema.idl.EchoingWiringFactory
import graphql.schema.idl.MockedWiringFactory
import graphql.schema.idl.NoopWiringFactory
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.ScalarWiringEnvironment
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.WiringFactory
import graphql.schema.idl.errors.DirectiveIllegalArgumentTypeError
import graphql.schema.idl.errors.DirectiveIllegalLocationError
import graphql.schema.idl.errors.DirectiveIllegalReferenceError
import graphql.schema.idl.errors.DirectiveMissingNonNullArgumentError
import graphql.schema.idl.errors.DirectiveRedefinitionError
import graphql.schema.idl.errors.DirectiveUndeclaredError
import graphql.schema.idl.errors.DirectiveUnknownArgumentError
import graphql.schema.idl.errors.IllegalNameError
import graphql.schema.idl.errors.InterfaceFieldArgumentNotOptionalError
import graphql.schema.idl.errors.InterfaceFieldArgumentRedefinitionError
import graphql.schema.idl.errors.InterfaceFieldRedefinitionError
import graphql.schema.idl.errors.InterfaceImplementedMoreThanOnceError
import graphql.schema.idl.errors.InterfaceImplementingItselfError
import graphql.schema.idl.errors.InterfaceWithCircularImplementationHierarchyError
import graphql.schema.idl.errors.MissingInterfaceFieldArgumentsError
import graphql.schema.idl.errors.MissingInterfaceFieldError
import graphql.schema.idl.errors.MissingInterfaceTypeError
import graphql.schema.idl.errors.MissingScalarImplementationError
import graphql.schema.idl.errors.MissingTransitiveInterfaceError
import graphql.schema.idl.errors.MissingTypeError
import graphql.schema.idl.errors.MissingTypeResolverError
import graphql.schema.idl.errors.QueryOperationMissingError
import graphql.schema.idl.errors.SchemaMissingError
import graphql.schema.idl.errors.SchemaRedefinitionError
import graphql.schema.idl.errors.TypeExtensionDirectiveRedefinitionError
import graphql.schema.idl.errors.TypeExtensionEnumValueRedefinitionError
import graphql.schema.idl.errors.TypeExtensionFieldRedefinitionError
import graphql.schema.idl.errors.TypeExtensionMissingBaseTypeError
import graphql.schema.idl.errors.TypeRedefinitionError
import graphql.schema.idl.errors.UnionTypeError
import graphql.schema.validation.SchemaValidationErrorCollector
import graphql.util.NodeAdapter
import graphql.util.NodeZipper
import graphql.validation.ValidationError
import io.github.susimsek.mscommonweb.graphql.exception.FieldError
import io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties
import io.github.susimsek.mscommonweb.graphql.scalar.LocalDateCoercing
import io.github.susimsek.mscommonweb.graphql.scalar.LocalDateTimeCoercing
import io.github.susimsek.mscommonweb.graphql.scalar.OffsetDateTimeCoercing
import io.github.susimsek.mscommonweb.graphql.scalar.ScalarUtil
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.nativex.hint.ResourceHint
import org.springframework.nativex.hint.TypeAccess
import org.springframework.nativex.hint.TypeHint
import org.springframework.nativex.type.NativeConfiguration

@TypeHint(
    types = [
        GraphQlRepository::class],
    access = [TypeAccess.QUERY_PUBLIC_METHODS]
)

@TypeHint(
    typeNames = ["graphql.analysis.QueryTraversalContext",
        "graphql.schema.idl.SchemaParseOrder",
        "graphql.schema.idl.ArgValueOfAllowedTypeChecker",
        "graphql.schema.idl.SchemaTypeDirectivesChecker",
        "graphql.schema.idl.errors.BaseError",
        "graphql.execution.instrumentation.fieldvalidation.FieldValidationSupport",
        "graphql.execution.instrumentation.fieldvalidation.FieldValidationSupport\$FieldAndArgError",
        "io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties\$ScalarDefinition",
        "graphql.schema.GraphQLScalarType\$Builder"],
    types = [Argument::class, ArrayValue::class, Boolean::class, BooleanValue::class,
        DataFetchingEnvironment::class, Directive::class, DirectiveDefinition::class,
        DirectiveLocation::class, Document::class, EnumTypeDefinition::class,
        EnumTypeExtensionDefinition::class, EnumValue::class, EnumValueDefinition::class,
        Execution::class, Field::class, FieldDefinition::class, FloatValue::class, FragmentDefinition::class,
        FragmentSpread::class, GraphQL::class, GraphQLArgument::class, GraphQLCodeRegistry.Builder::class,
        GraphQLDirective::class, GraphQLEnumType::class, GraphQLEnumValueDefinition::class,
        GraphQLFieldDefinition::class, GraphQLInputObjectField::class, GraphQLInputObjectType::class,
        GraphQLInterfaceType::class, GraphQLList::class, GraphQLNamedType::class, GraphQLNonNull::class,
        GraphQLObjectType::class, GraphQLOutputType::class, GraphQLScalarType::class, GraphQLSchema::class,
        GraphQLSchemaElement::class, GraphQLUnionType::class, ImplementingTypeDefinition::class, InlineFragment::class,
        InputObjectTypeDefinition::class, InputObjectTypeExtensionDefinition::class, InputValueDefinition::class,
        IntValue::class, InterfaceTypeDefinition::class, InterfaceTypeExtensionDefinition::class, MutableList::class,
        ListType::class, NodeAdapter::class, NodeZipper::class, NonNullType::class, NullValue::class,
        ObjectField::class, ObjectTypeDefinition::class, ObjectTypeExtensionDefinition::class, ObjectValue::class,
        OperationDefinition::class, OperationTypeDefinition::class, ParserOptions::class,
        QueryVisitorFieldArgumentEnvironment::class, QueryVisitorFieldArgumentInputValue::class,
        ScalarTypeDefinition::class, ScalarTypeExtensionDefinition::class,
        SchemaDefinition::class, SchemaExtensionDefinition::class, SchemaValidationErrorCollector::class,
        SelectionSet::class, StringValue::class, TypeDefinition::class, TypeName::class, UnionTypeDefinition::class,
        UnionTypeExtensionDefinition::class, VariableDefinition::class, VariableReference::class,  ScalarUtil::class,
        FederationDirectives::class, FederationSdlPrinter::class, AbstractDescribedNode::class, AbstractNode::class,
        Definition::class, DescribedNode::class, DirectivesContainer::class, ImplementingTypeDefinition::class, NamedNode::class,
        SDLDefinition::class, SDLNamedDefinition::class, ScalarValue::class, Selection::class, SelectionSetContainer::class,
        Type::class, TypeDefinition::class, Value::class, Node::class, GraphqlBooleanCoercing::class, GraphqlFloatCoercing::class,
        GraphqlIDCoercing::class, GraphqlIntCoercing::class, GraphqlStringCoercing::class, EchoingWiringFactory::class, MockedWiringFactory::class,
        LocalDateCoercing::class, LocalDateTimeCoercing::class, OffsetDateTimeCoercing::class, Coercing::class, TypeDefinitionRegistry::class,
        RuntimeWiring::class, TypeResolver::class, TypeResolverProxy::class, GraphQLError::class, AbortExecutionException::class,
        CoercingParseLiteralException::class, CoercingParseValueException::class, CoercingSerializeException::class, DirectiveIllegalArgumentTypeError::class,
        DirectiveIllegalLocationError::class, DirectiveIllegalReferenceError::class, DirectiveMissingNonNullArgumentError::class,
        DirectiveRedefinitionError::class, DirectiveUndeclaredError::class, DirectiveUnknownArgumentError::class, ExceptionWhileDataFetching::class,
        GraphqlErrorException::class, GraphqlErrorBuilder::class, SerializationError::class,
        GraphQlDateTimeProperties::class, FieldError::class, InvalidCursorException::class, InvalidPageSizeException::class,
        UnresolvedTypeError::class, ValidationError::class, UnionTypeError::class, TypeRedefinitionError::class, TypeMismatchError::class,
        TypeExtensionMissingBaseTypeError::class, TypeExtensionFieldRedefinitionError::class,   TypeExtensionEnumValueRedefinitionError::class,
        TypeExtensionDirectiveRedefinitionError::class, SchemaRedefinitionError::class, SchemaMissingError::class, QueryOperationMissingError::class,
        NonNullableFieldWasNullError::class,  MissingTypeResolverError::class, MissingTypeError::class, MissingTransitiveInterfaceError::class,
        MissingScalarImplementationError::class, MissingRootTypeException::class, MissingInterfaceTypeError::class, MissingInterfaceFieldError::class,
        MissingInterfaceFieldArgumentsError::class, InvalidSyntaxError::class, InterfaceWithCircularImplementationHierarchyError::class,
        InterfaceFieldArgumentNotOptionalError::class, InputMapDefinesTooManyFieldsException::class, InterfaceImplementingItselfError::class,
        InterfaceImplementedMoreThanOnceError::class, InterfaceFieldRedefinitionError::class, InterfaceFieldArgumentRedefinitionError::class,
        ScalarWiringEnvironment::class, WiringFactory::class, CombinedWiringFactory::class, NoopWiringFactory::class,
        IllegalNameError::class ], access = [
        TypeAccess.PUBLIC_CLASSES, TypeAccess.PUBLIC_CONSTRUCTORS, TypeAccess.PUBLIC_FIELDS, TypeAccess.PUBLIC_METHODS,
        TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_FIELDS,
        TypeAccess.DECLARED_METHODS]
)
@ResourceHint(patterns = ["graphiql/index.html"])
@Lazy(false)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = ["org.springframework.nativex.NativeListener"])
class GraphQlNativeHints : NativeConfiguration
