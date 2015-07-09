package com.odcgroup.page.ui.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.ui.edit.PreviewEditPartFactory;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.VerticalLayout;
import com.odcgroup.page.ui.figure.conditional.ConditionalBodyFigure;
import com.odcgroup.page.ui.figure.conditional.ConditionalFigure;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This class provides methods to save the condition of a gived page it's generate an image for each conditional widgets
 * and their child (condition)x
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class SaveImageUtils {
	
	/** default image's width */
	private static int DEFAULT_IMAGE_MIN_WIDTH = 800; //1280;

	/** default image's height */
	private static int DEFAULT_IMAGE_MIN_HEIGHT = 800; //1024;
	
	/** */
	private Shell shell;

    /** The graphical viewer used to set the figure to save. */
    private GraphicalViewer viewer;

    /** The resource to load. */
    private Resource resource;

    /**
     * Constructor
     */
    private SaveImageUtils() {
    }
    

    /**
     * Create an image for the given file.
     * 
     * @param file The input file
     * @param filename The filename (prefix) used for the generated images
     * @param path The path
     * @param imageFormat The format
     */
    public static void createImages(IFile file, String filename, IPath path, int imageFormat) {
        try {
            new SaveImageUtils().createImageFiles(file, filename, path, imageFormat);
        } catch (IOException ioe) {
            Logger.error("Unable to create the image file: " + filename, ioe);
            throw new PageException("Unable to create the image file: " + filename, ioe);
        }
    }
    
    /**
     * Create an image for the given file and each condition.
     * 
     * @param mResource The ofsmodelResource
     * @param filename The filename (prefix) used for the generated images
     * @param path The path
     * @param imageFormat The format
     */
    public static void createImagesAndConditions(IOfsModelResource mResource, String filename, IPath path, int imageFormat) {
        try {
            new SaveImageUtils().createImageFilesAndConditions(mResource, filename, path, imageFormat);
        } catch (IOException ioe) {
            Logger.error("Unable to create the image file: " + filename, ioe);
            throw new PageException("Unable to create the image file: " + filename, ioe);
        } catch (InvalidMetamodelVersionException e) {
            Logger.error("Unable to create the image file: " + filename, e);
            throw new PageException("Unable to create the image file: " + filename, e);
		}
    }     
   
    
    /**
     * @param mResource 
     * @param filename
     * @param path
     * @param imageFormat
     * @throws IOException
     * @throws InvalidMetamodelVersionException 
     */
    private void createImageFilesAndConditions(IOfsModelResource mResource, String filename, IPath path, int imageFormat) throws IOException, InvalidMetamodelVersionException{
    	
    	EList<EObject> list = mResource.getEMFModel();
    	if (list.isEmpty()){
    		return;
    	}
    	Model model = (Model)list.get(0);
    	int style = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getStyle();
        try {
        	shell = new Shell((style & SWT.MIRRORED) != 0 ? SWT.RIGHT_TO_LEFT : SWT.NONE);
	        viewer = new ScrollingGraphicalViewer();
	        viewer.createControl(shell);
	        viewer.setEditDomain(new DefaultEditDomain(null));
	        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
	        IProject project = mResource.getOfsProject().getProject();
	        viewer.setEditPartFactory(new PreviewEditPartFactory(project, new CommandStack()));
	        viewer.setContents(model.getWidget()); 
	        
	        // Save a first version of the complete figure
	        WidgetEditPart wep = (WidgetEditPart) getRootEditPart().getChildren().get(0);
	        saveImage((IWidgetFigure) wep.getFigure(), path.toString() + File.separator + filename + "."
	                + getImageFormat(imageFormat), imageFormat);
	        
	        List<IWidgetFigure> clwfs = getConditionalFigures();
	        for (IWidgetFigure clwf : clwfs) {
	            String cn = clwf.getWidget().getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
	            List<IWidgetFigure> cwfs = getConditionFigures((ConditionalFigure) clwf);
	            for (IWidgetFigure cwf : cwfs) {
	                String conditionName = cwf.getWidget().getPropertyValue(PropertyTypeConstants.TECHNICAL_NAME);
	                saveImage(cwf, path.toString() + File.separator + filename + "_" + cn + "_" + conditionName + "."
	                        + getImageFormat(imageFormat), imageFormat);
	            }
	        }
        } finally {
        	if (shell != null) {
        		shell.close();
        	}
        }
    }
    
    /**
     * Create the image files for the given file.
     * 
     * @param file The IFile input
     * @param filename The filename (prefix) used for the generated images
     * @param path The path for the output
     * @param imageFormat The image format for the output
     * @throws IOException Thrown if an error occurs
     */
    private void createImageFiles(IFile file, String filename, IPath path, int imageFormat) throws IOException {
        if (file == null) {
            return;
        }
        try {
	        initializeResource(file);
	        initializeGraphicalViewer(file);
	
	        // Save a first version of the complete figure
	        WidgetEditPart wep = (WidgetEditPart) getRootEditPart().getChildren().get(0);
	        saveImage((IWidgetFigure) wep.getFigure(), path.toString() + File.separator + filename + "."
	                + getImageFormat(imageFormat), imageFormat);
        } finally {
        	if (shell != null) {
        		shell.close();
        	}
        }
        
    }  

    /**
     * Gets the String representation of the image format. {
     * 
     * @param imageFormat The image format
     * @return String returns the String representation of the image format
     */
    private String getImageFormat(int imageFormat) {
        if (imageFormat == SWT.IMAGE_JPEG) {
            return "jpeg";
        } else if (imageFormat == SWT.IMAGE_GIF) {
            return "gif";
        } else if (imageFormat == SWT.IMAGE_PNG) {
            return "png";
        } else if (imageFormat == SWT.IMAGE_BMP) {
            return "bmp";
        }
        return "gif";
    }

    /**
     * Save the image with the specified figure.
     * 
     * @param figure The figure to save
     * @param filename The file name
     * @param imageFormat the requested image format (jpeg, png, ...)
     * @throws IOException Thrown if an error occurs
     */
    private void saveImage(IWidgetFigure figure, String filename, int imageFormat) throws IOException {
        byte[] imageBytes = createImage(figure, imageFormat);
        FileOutputStream out = new FileOutputStream(filename);
        out.write(imageBytes);
        out.flush();
        out.close();
    }

    /**
     * Create an image for a given figure and a specified format.
     * 
     * @param figure the Figure to create an image for.
     * @param format one of SWT.IMAGE_BMP, SWT.IMAGE_GIF , SWT.IMAGE_JPEG, or SWT.IMAGE_PNG
     * @return byte [] returns an array of bytes that include the image
     */
    private byte[] createImage(IWidgetFigure figure, int format) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        LayoutManager layout = figure.getLayoutManager();
        if (layout == null) {
            layout = new VerticalLayout(figure.getFigureContext());
        }

        int fwidth = DEFAULT_IMAGE_MIN_WIDTH;
        int fheight  = DEFAULT_IMAGE_MIN_HEIGHT;
        
        // Sets the default bounds
        figure.setBounds(new Rectangle(0, 0, fwidth, fheight));
        figure.setVisible(true);
        
        // Layout all the children of the condition to fit the correct bounds
        layout.layout(figure);
        layoutChildren(figure);

        boolean redoLayout = false;
        if (figure.getMinHeight() > fheight) {
        	fheight = figure.getMinHeight();
        	redoLayout = true;
        } 
        if (figure.getMinWidth() < DEFAULT_IMAGE_MIN_WIDTH) {
        	fwidth = DEFAULT_IMAGE_MIN_WIDTH;
        } else if (figure.getMinWidth() > fwidth) {
        	fwidth = figure.getMinWidth();
        	redoLayout = true;
        }
        
        if (fwidth < 0) {
        	fwidth = DEFAULT_IMAGE_MIN_WIDTH;
        }
        if (fheight < 0) {
        	fheight = DEFAULT_IMAGE_MIN_HEIGHT;
        }
        
        if (redoLayout) {
            figure.setBounds(new Rectangle(0, 0, fwidth, fheight));
            figure.setVisible(true);
        }

        Image image = null;
        GC gc = null;
        Graphics g = null;
        try {
        	
            Rectangle b = new Rectangle(0, 0, fwidth/*800*/, fheight/*figure.getMaxHeight()*/);
            if (figure.getChildren().size() > 0) {
                IFigure f = (IFigure) figure.getChildren().get(figure.getChildren().size() - 1);
                b = f.getBounds();
            }
            int height = b.y + b.height + 2;
            image = new Image(null, fwidth/*800*/, height);
            gc = new GC(image);
            g = new SWTGraphics(gc);
            figure.paint(g);
            
            // draw a border around the image
            gc.setLineWidth(2);
            gc.setLineWidth(SWT.LINE_SOLID);
            gc.setForeground(ColorConstants.black); 
            g.drawPolygon(new int [] {0,0, 0, height-1, fwidth-1, height-1, fwidth-1, 0});
            
            
            ImageLoader loader = new ImageLoader();
            loader.data = new ImageData[1];
            ImageData imageData = image.getImageData();
            // If the image depth is 8 bits or less, then we can use the existing image data.
            if (imageData.depth <= 8) {
                loader.data[0] = imageData;
            } else {
                // Gets an 8 bit imageData for the image
                ImageData newImageData = get8BitPaletteImageData(imageData);
                loader.data[0] = newImageData;
            }
            loader.save(result, format);
        } finally {
            if (g != null) {
                g.dispose();
            }
            if (gc != null) {
                gc.dispose();
            }
            if (image != null) {
                image.dispose();
            }
        }
        return result.toByteArray();
    }
    

    /**
     * Initialize the graphical viewer.
     * 
     * @param file The file that will be used by the factory
     */
    private void initializeGraphicalViewer(IFile file) {
        int style = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getStyle();
        shell = new Shell((style & SWT.MIRRORED) != 0 ? SWT.RIGHT_TO_LEFT : SWT.NONE);
        viewer = new ScrollingGraphicalViewer();
        viewer.createControl(shell);
        viewer.setEditDomain(new DefaultEditDomain(null));
        viewer.setRootEditPart(new ScalableFreeformRootEditPart());
        viewer.setEditPartFactory(new PreviewEditPartFactory(file.getProject(), new CommandStack()));
        Model model = (Model) resource.getContents().get(0);
        viewer.setContents(model.getWidget());
    }

    /**
     * Gets the list of Conditional Widget.
     * 
     * @return List<Widget> returns the list of ConditionalWidget
     */
    private List<IWidgetFigure> getConditionalFigures() {
        EditPart rootEditPart = getRootEditPart();
        IFigure rootFigure = ((LayerManager) rootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);// rootEditPart.getFigure();
        List<IWidgetFigure> result = getConditionalFigures(rootFigure, null);
        return result;
    }

    /**
     * Gets the root EditPart.
     * 
     * @return EditPart
     */
    private EditPart getRootEditPart() {
        return (EditPart) viewer.getEditPartRegistry().get(LayerManager.ID);
    }

    /**
     * Gets the list of the CondiditionalFigure.
     * 
     * @param rootFigure The root figure
     * @param filteredFigures The list of filtered figure
     * 
     * @return List<IFigure> returns a list of ConditionalFigure's
     */
    private List<IWidgetFigure> getConditionalFigures(IFigure rootFigure,
            List<IWidgetFigure> filteredFigures) {
        if (filteredFigures == null) {
            filteredFigures = new ArrayList<IWidgetFigure>();
        }
        List<IWidgetFigure> children = rootFigure.getChildren();
        for (IFigure child : children) {
            if (child instanceof ConditionalFigure) {
                filteredFigures.add((IWidgetFigure) child);
            }
            getConditionalFigures(child, filteredFigures);
        }
        return filteredFigures;
    }

    /**
     * Gets the list of the Condition Figure contained inside a particular. Conditional Figure
     * 
     * @param conditionalFigure The Condition figure
     * @return List<IFigure> returns the list of a condition widget
     */
    private List<IWidgetFigure> getConditionFigures(ConditionalFigure conditionalFigure) {
        List<IWidgetFigure> figures = conditionalFigure.getAllChildren();
        List<IWidgetFigure> conditions = new ArrayList<IWidgetFigure>();
        for (IWidgetFigure figure : figures) {
            if (figure instanceof ConditionalBodyFigure) {
                conditions.add(figure);
            }
        }
        return conditions;
    }

    /**
     * Load the desired resource.
     * 
     * @param file The file
     */
    private void initializeResource(IFile file) {
        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
    	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
        ResourceSet resourceSet = ofsProject.getModelResourceSet();
        resource = resourceSet.getResource(uri, true);
    }

    /**
     * Layout all the children of the given figure
     * 
     * @param figure The figure to layout
     */
    private void layoutChildren(IFigure figure) {
        IWidgetFigure a = (IWidgetFigure) figure;
        List<IWidgetFigure> children = a.getAllChildren();
        for (IWidgetFigure child : children) {
            LayoutManager layoutManager = child.getLayoutManager();
            if (layoutManager != null) {
                layoutManager.layout(child);
            }
            layoutChildren(child);
        }
    }
    
    /**
     * Retrieve an image data with an 8 bit palette for an image. We assume that
     * the image has less than 256 colours.
     * 
     * @param imageData
     *            the imageData for the image.
     * @return the new 8 bit imageData or null if the image has more than 256
     *         colours.
     */
    private ImageData get8BitPaletteImageData(ImageData imageData) {
        PaletteData palette = imageData.palette;
        RGB colours[] = new RGB[256];
        PaletteData newPaletteData = new PaletteData(colours);
        ImageData newImageData = new ImageData(imageData.width,
            imageData.height, 8, newPaletteData);

        int lastPixel = -1;
        int newPixel = -1;
        for (int i = 0; i < imageData.width; ++i) {
            for (int j = 0; j < imageData.height; ++j) {
                int pixel = imageData.getPixel(i, j);

                if (pixel != lastPixel) {
                    lastPixel = pixel;

                    RGB colour = palette.getRGB(pixel);
                    for (newPixel = 0; newPixel < 256; ++newPixel) {
                        if (colours[newPixel] == null) {
                            colours[newPixel] = colour;
                            break;
                        }
                        if (colours[newPixel].equals(colour))
                            break;
                    }

                    if (newPixel > 256) {
                        // Diagram has more than 256 colors, return null
                        return null;
                    }
                }

                newImageData.setPixel(i, j, newPixel);
            }
        }

        RGB colour = new RGB(0, 0, 0);
        for (int k = 0; k < 256; ++k) {
            if (colours[k] == null)
                colours[k] = colour;
        }

        return newImageData;
    } 
}