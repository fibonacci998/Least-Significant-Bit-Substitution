# Least-Significant-Bit-Substitution
Hiding data in image using LSB (Least Significant Bit) Substitution
# Background
Image hiding is a technique used to embed secret data in image, i.e., data is hidden in a publishable image, but the hiding process does not damage the original image. The image in which the secret data is hidden is called a stego-image. The stego-image will not attract suspicion so that attacks can be prevented. But an intended receiver can successfully decode the secret data hidden in the stego-image.

The simplest method for hiding data is the least significant bit (LSB) method. It hides data in the least significant bit of each image pixel. Because the variation between the original pixel value and the embedded pixel value is small, the image quality is often not bad even after hiding progress is completed.  

Ref: 	A LS Substitution Oriented Image Hiding Strategy Using Genetic Algorithms
	Ming-Ni Wu, Min-Hui Lin, and Chin-Chen Chang
