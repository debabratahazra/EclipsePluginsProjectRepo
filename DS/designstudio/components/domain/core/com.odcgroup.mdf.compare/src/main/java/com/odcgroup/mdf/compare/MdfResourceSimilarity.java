package com.odcgroup.mdf.compare;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.match.internal.statistic.NameSimilarity;
import org.eclipse.emf.ecore.resource.Resource;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class MdfResourceSimilarity {
	/**
	 * Utility classes don't need to be instantiated.
	 */
	private MdfResourceSimilarity() {
		// hides default construtor
	}

	/**
	 * This will try and find a resource in <code>candidates</code> similar to <code>resource</code>.
	 * 
	 * @param resource
	 *            The resource we seek a similar to in the given resourceSet.
	 * @param candidates
	 *            candidate resources.
	 * @return The most similar resource to <code>resource</code> we could find in <code>resourceSet</code>.
	 */
	public static Resource findMatchingResource(Resource resource, List<Resource> candidates) {
		final double resourceSimilarityThreshold = 0.7d;
		final URI referenceURI = resource.getURI();
		if (candidates.size() == 1)
			return candidates.get(0);

		Resource mostSimilar = null;
		double highestSimilarity = -1;
		for (final Resource candidate : candidates) {
			final URI candidateURI = candidate.getURI();
			if (referenceURI.fileExtension().equals(candidateURI.fileExtension())) {
				final String[] referenceSegments = referenceURI.trimFileExtension().segments();
				final String[] candidateSegments = candidateURI.trimFileExtension().segments();
				final double similarity = resourceURISimilarity(referenceSegments, candidateSegments);
				if (similarity > highestSimilarity) {
					highestSimilarity = similarity;
					mostSimilar = candidate;
				}
			}
		}

		// Consider dissimilar
		if (highestSimilarity < resourceSimilarityThreshold) {
			mostSimilar = null;
		}
		return mostSimilar;
	}

	/**
	 * This will compute the similarity between the two given URIs.
	 * 
	 * @param reference
	 *            The reference URI.
	 * @param candidate
	 *            Candidate we wish compared to the reference.
	 * @return A double comprised between <code>0</code> and <code>1</code> included, <code>1</code> being
	 *         equal and <code>0</code> different.
	 */
	public static double computeURISimilarity(URI reference, URI candidate) {
		if (reference.equals(candidate))
			return 1d;
		final double segmentsWeight = 0.4d;
		final double fragmentWeight = 0.6d;
		final double almostEqual = 0.999d;

		double similarity = 0d;
		if (reference.scheme() != null && reference.scheme().equals(candidate.scheme())) {
			final String referenceFragment = reference.fragment();
			final String candidateFragment = candidate.fragment();
			final String[] referenceSegments = reference.segments();
			final String[] candidateSegments = candidate.segments();
			final double segmentSimilarity;
			if (referenceSegments.length>0 && candidateSegments.length>0)
				segmentSimilarity = resourceURISimilarity(referenceSegments, candidateSegments);
			else
				segmentSimilarity = 1d;
			final double fragmentSimilarity;
			if (referenceFragment != null && candidateFragment != null) {
				fragmentSimilarity = fragmentURISimilarity(referenceFragment, candidateFragment);
			} else {
				fragmentSimilarity = 1d;
			}

			similarity = segmentSimilarity * segmentsWeight + fragmentSimilarity * fragmentWeight;
		}
		if (similarity > almostEqual)
			similarity = 1d;
		return similarity;
	}

	/**
	 * This will compute the similarity of two URI fragments.
	 * 
	 * @param reference
	 *            The reference fragment.
	 * @param candidate
	 *            Candidate for which the similarity to <code>reference</code> is to be computed.
	 * @return A double comprised between <code>0</code> and <code>1</code> included, <code>1</code> being
	 *         equal and <code>0</code> different.
	 */
	private static double fragmentURISimilarity(String reference, String candidate) {
		return NameSimilarity.nameSimilarityMetric(reference, candidate);
	}

	/**
	 * This will compute the similarity of two URIs based on their segments (minus file extension).
	 * 
	 * @param reference
	 *            The reference URI.
	 * @param candidate
	 *            Candidate for which the similarity to <code>reference</code> is to be computed.
	 * @return A double comprised between <code>0</code> and <code>1</code> included, <code>1</code> being
	 *         equal and <code>0</code> different.
	 */
	private static double resourceURISimilarity(String[] reference, String[] candidate) {
		final double nameWeight = 0.6;
		final double equalSegmentWeight = 0.4;
		final double almostEqual = 0.999;
		
		final String referenceName = reference[reference.length-1];
		final String candidateName = candidate[candidate.length-1];
		final double nameSimilarity = NameSimilarity.nameSimilarityMetric(referenceName, candidateName);

		if (reference.length == 1 || candidate.length == 1 || nameSimilarity > almostEqual)
			return nameSimilarity;

		double equalSegments = 0d;
		int referenceIndex = reference.length - 2;
		int candidateIndex = candidate.length - 2;
		while (referenceIndex >= 0 && candidateIndex >= 0) {
			if (reference[referenceIndex].equals(candidate[candidateIndex])) {
				equalSegments++;
			}
			referenceIndex--;
			candidateIndex--;
		}
		return nameSimilarity * nameWeight + equalSegments * 2 / (reference.length + candidate.length - 2)
				* equalSegmentWeight;
	}


}
