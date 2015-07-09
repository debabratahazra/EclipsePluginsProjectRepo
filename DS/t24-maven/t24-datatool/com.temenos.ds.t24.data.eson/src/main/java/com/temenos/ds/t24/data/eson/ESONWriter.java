package com.temenos.ds.t24.data.eson;

import org.apache.commons.io.output.StringBuilderWriter;

import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse.Field;

/**
 * ESON mapper.
 *
 * @author Michael Vorburger
 * @deprecated Use ESONComponentFrameworkMapper instead
 */
public class ESONWriter {

	// TODO: Yann to rewrite this with ESONDataModel
	public boolean isIncludingEmptyFieldsAsComments = false;

	public String toESON(GetResourceResponse resource) {
		StringBuilder sb = new StringBuilder();
		StringBuilderWriter writer = new StringBuilderWriter(sb);

		writer.append("import t24.applications.*\n\n");
		writer.append(resource.resourceName);
		writer.append(" {\n");
		for (Field field : resource.fields) {
			if (!isIncludingEmptyFieldsAsComments && isNullOrEmpty(field.value))
				continue;
			writer.append("    ");
			if (isIncludingEmptyFieldsAsComments && isNullOrEmpty(field.value))
				writer.append(" // ");
			writer.append(field.name);
			writer.append(": ");
			if (!isNullOrEmpty(field.value)) {
				// TODO if (???) condition
				writer.append('\"');
				writer.append(field.value);
				writer.append('\"');
				if (field.mv != 1 && field.sv != 1) {
					writer.append(" // "); // TODO remove
					writer.append(field.mv.toString());
					writer.append('-');
					writer.append(field.sv.toString());
				}
			}
			writer.append('\n');
		}
		writer.append("}\n");
		return sb.toString();
	}

	private boolean isNullOrEmpty(/* TODO @Nullable */String string) {
		return string == null || string.isEmpty();
	}
}
