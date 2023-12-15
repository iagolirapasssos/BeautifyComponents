package com.bosonshiggs.beautifycomponents;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

import com.bosonshiggs.beautifycomponents.helpers.ColorFilterType;

import java.io.IOException;

import com.bosonshiggs.beautifycomponents.helpers.AnimationEffectType;
import com.bosonshiggs.beautifycomponents.helpers.GradientOrientation;
import com.bosonshiggs.beautifycomponents.helpers.ShapeType;

import android.content.Context;

import android.os.Build;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.content.res.ColorStateList;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.InputStream; // Para InputStream
import java.io.IOException; // Para IOException
import android.graphics.Paint; // Para Paint
import android.graphics.BlurMaskFilter; // Para BlurMaskFilter
import android.graphics.drawable.ShapeDrawable; // Para ShapeDrawable
import android.graphics.drawable.shapes.RectShape; // Para RectShape

import android.graphics.drawable.shapes.OvalShape;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.LayerDrawable;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;
import android.graphics.Bitmap;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import android.graphics.drawable.StateListDrawable;


import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout;

import android.app.Activity;

import android.util.Log;

@DesignerComponent(
    version = 2,
    description = "Extensão para embelezar componentes com cantos arredondados e efeitos metálicos.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png"
)
@SimpleObject(external = true)
public class BeautifyComponents extends AndroidNonvisibleComponent {
	private String LOG_NAME = "BeautifyComponents";
    private boolean flagLog = false;
    
    public BeautifyComponents(ComponentContainer container) {
        super(container.$form());  
    }
    @SimpleFunction(description = "Applies a shadow effect to the corners of a visual component.\n" +
            "Parameters:\n" +
            "- component: The component to apply the shadow effect.\n" +
            "- int shadowColor: The color of the shadow.\n" +
            "- float shadowRadius: The radius of the shadow.\n" +
            "- float dx: The horizontal offset of the shadow.\n" +
            "- float dy: The vertical offset of the shadow.\n" +
            "Return: None")
    public void AddShadowEffect(AndroidViewComponent component, int shadowColor, float shadowRadius, float dx, float dy) {
        final View view = component.getView();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(shadowRadius);
            view.setTranslationZ(shadowRadius);
        }

        if (view instanceof TextView) {
            // Aplicar sombra somente se o View for um TextView ou subclasses
        	try {
            	((TextView) view).setShadowLayer(shadowRadius, dx, dy, shadowColor);
        	} catch (Exception e) {
        		if (flagLog) Log.e(LOG_NAME, "AddShadowEffect - Error: " + e.getMessage(), e);
        		ReportError("AddShadowEffect - Error: " + e.getMessage());
        	}
        }
        // Para outros tipos de View, você pode precisar de abordagens específicas
    }
    
    @SimpleFunction(description = "Applies a shadow effect to all components within a container and its subcontainers, except linear layouts.\n" +
            "Parameters:\n" +
            "- container: The container to apply the shadow effect.\n" +
            "- int shadowColor: The color of the shadow.\n" +
            "- float shadowRadius: The radius of the shadow.\n" +
            "- float dx: The horizontal offset of the shadow.\n" +
            "- float dy: The vertical offset of the shadow.\n" +
            "Return: None")
    public void AddShadowEffectToAllViews(AndroidViewComponent container, int shadowColor, float shadowRadius, float dx, float dy) {
        View view = container.getView();
        if (view instanceof ViewGroup) {
        	try {
        		applyShadowEffectRecursively((ViewGroup) view, shadowColor, shadowRadius, dx, dy);
        	} catch (Exception e) {
        		if (flagLog) Log.e(LOG_NAME, "AddShadowEffectToAllViews - Error: " + e.getMessage(), e);
        		ReportError("AddShadowEffectToAllViews - Error: " + e.getMessage());
        	}
        }
    }

    private void applyShadowEffectRecursively(ViewGroup viewGroup, int shadowColor, float shadowRadius, float dx, float dy) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            // Aplica o efeito de sombra se não for um LinearLayout
            if (!(child instanceof LinearLayout)) {
                applyShadowEffectToView(child, shadowColor, shadowRadius, dx, dy);
            }

            // Continua a aplicação recursiva se for um ViewGroup
            if (child instanceof ViewGroup) {
                applyShadowEffectRecursively((ViewGroup) child, shadowColor, shadowRadius, dx, dy);
            }
        }
    }

    private void applyShadowEffectToView(View view, int shadowColor, float shadowRadius, float dx, float dy) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(shadowRadius);
            view.setTranslationZ(shadowRadius);
        }

        if (view instanceof TextView) {
            ((TextView) view).setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }
    }

    
    @SimpleFunction(description = "Changes the opacity of a visual component.\n" +
            "Parameters:\n" +
            "- component: The component to change the opacity.\n" +
            "- float opacity: The desired opacity level (from 0.0 to 1.0).\n" +
            "Return: None")
    public void ChangeOpacity(AndroidViewComponent component, float opacity) {
        View view = component.getView();
        try {
        	view.setAlpha(opacity);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ChangeOpacity - Error: " + e.getMessage(), e);
    		ReportError("ChangeOpacity - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Rotates a visual component.\n" +
            "Parameters:\n" +
            "- component: The component to rotate.\n" +
            "- float angle: The rotation angle in degrees.\n" +
            "Return: None")
    public void RotateComponent(AndroidViewComponent component, float angle) {
        View view = component.getView();
        try {
        	view.setRotation(angle);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "RotateComponent - Error: " + e.getMessage(), e);
    		ReportError("RotateComponent - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Scales a visual component.\n" +
            "Parameters:\n" +
            "- component: The component to scale.\n" +
            "- float scaleX: The scale factor in the X direction.\n" +
            "- float scaleY: The scale factor in the Y direction.\n" +
            "Return: None")
    public void ScaleComponent(AndroidViewComponent component, float scaleX, float scaleY) {
        View view = component.getView();
        try {
	        view.setScaleX(scaleX);
	        view.setScaleY(scaleY);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ScaleComponent - Error: " + e.getMessage(), e);
    		ReportError("ScaleComponent - Error: " + e.getMessage());
    	}
    }

    @SimpleFunction(description = "Applies a color filter to a visual component.\n" +
            "Parameters:\n" +
            "- component: The component to apply the color filter.\n" +
            "- String filterType: The type of color filter (e.g., grayscale, sepia).\n" +
            "Return: None")
    public void ApplyColorFilter(AndroidViewComponent component, @Options(ColorFilterType.class) String filterType) {
        ColorFilterType colorFilterType = ColorFilterType.fromUnderlyingValue(filterType);
        View view = component.getView();
        try {
	        ColorMatrixColorFilter colorFilter = createColorFilter(filterType);
	        
	        if (view instanceof ImageView) {
	            ((ImageView) view).setColorFilter(colorFilter);
	        } else {
	            // Para outros tipos de View, pode ser necessário um tratamento diferente
	            view.getBackground().setColorFilter(colorFilter);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyColorFilter - Error: " + e.getMessage(), e);
    		ReportError("ApplyColorFilter - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Returns the X coordinate of a visual component.")
    public float GetComponentX(AndroidViewComponent component) {
        View view = component.getView();
        try {
            return view.getX();
        } catch (Exception e) {
            if (flagLog) Log.e(LOG_NAME, "GetComponentX - Error: " + e.getMessage(), e);
            ReportError("GetComponentX - Error: " + e.getMessage());
            return 0; // Retorna 0 em caso de erro
        }
    }

    @SimpleFunction(description = "Returns the Y coordinate of a visual component.")
    public float GetComponentY(AndroidViewComponent component) {
        View view = component.getView();
        try {
            return view.getY();
        } catch (Exception e) {
            if (flagLog) Log.e(LOG_NAME, "GetComponentY - Error: " + e.getMessage(), e);
            ReportError("GetComponentY - Error: " + e.getMessage());
            return 0; // Retorna 0 em caso de erro
        }
    }
    
    @SimpleFunction(description = "Overlays a visual component above others at specific coordinates.\n" +
            "Parameters:\n" +
            "- component: The component to position.\n" +
            "- float x: The X coordinate.\n" +
            "- float y: The Y coordinate.\n" +
            "Return: None")
    public void OverlayComponentAt(AndroidViewComponent component, float x, float y) {
        View view = component.getView();
        try {
	        // Certifique-se de que o pai da View é um FrameLayout ou RelativeLayout
	        if (view.getParent() instanceof FrameLayout || view.getParent() instanceof RelativeLayout) {
	            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
	            params.leftMargin = (int) x;
	            params.topMargin = (int) y;
	            view.setLayoutParams(params);
	        } else {
	            // Se o pai não é um FrameLayout/RelativeLayout, ajustamos a posição diretamente
	            view.setX(x);
	            view.setY(y);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "OverlayComponentAt - Error: " + e.getMessage(), e);
    		ReportError("OverlayComponentAt - Error: " + e.getMessage());
    	}
    }

    @SimpleFunction(description = "Applies a 3D effect to the corners of a visual component with customized text color.\n" +
            "Parameters:\n" +
            "- component: The component to apply the 3D effect.\n" +
            "- float cornerRadius: The radius of the corners.\n" +
            "- float borderWidth: The width of the border.\n" +
            "- int backgroundColor: The background color.\n" +
            "- int borderColor: The border color.\n" +
            "- int shadowColor: The shadow color.\n" +
            "- int elevation: The elevation level.\n" +
            "- int textColor: The text color.\n" +
            "- boolean isItalic: If true, the text is italic.\n" +
            "- boolean isBold: If true, the text is bold.\n" +
            "- boolean applyToViewChildren: If true, apply to child views.\n" +
            "- boolean applyToViewGroupChildren: If true, apply to children of view groups.\n" +
            "- boolean applyOrganizationVerticalHorizontal: Apply vertical/horizontal organization.\n" +
            "Return: None")
    public void Apply3DEffect(
    		AndroidViewComponent component, 
    		float cornerRadius,
    		float borderWidth,
    		int backgroundColor, 
    		int borderColor,  
    		int shadowColor, 
    		int elevation, 
    		int textColor, 
    		boolean isItalic, 
    		boolean isBold, 
    		boolean applyToViewChildren, 
    		boolean applyToViewGroupChildren, 
    		boolean applyOrganizationVerticalHorizontal
    		) 
    {
        View view = component.getView();
        try {
	        if (view instanceof ViewGroup) {
	            ViewGroup viewGroup = (ViewGroup) view;
	            if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
	            	apply3DEffectToViewGroup(viewGroup, cornerRadius, borderWidth, backgroundColor, borderColor, shadowColor, elevation, textColor, isItalic, isBold, applyToViewChildren, applyToViewGroupChildren, applyOrganizationVerticalHorizontal);
	            }
	        } else {
	            apply3DEffectToView(view, cornerRadius, borderWidth, backgroundColor, borderColor, shadowColor, elevation, textColor, isItalic, isBold);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "Apply3DEffect - Error: " + e.getMessage(), e);
    		ReportError("Apply3DEffect - Error: " + e.getMessage());
    	}
    }

    @SimpleFunction(description = "Rounds the corners of a visual component and adjusts colors.\n" +
            "Parameters:\n" +
            "- component: The component to round the corners.\n" +
            "- float cornerRadius: The radius of the corners.\n" +
            "- float borderWidth: The width of the border.\n" +
            "- int backgroundColor: The background color.\n" +
            "- int borderColor: The border color.\n" +
            "- int childBackgroundColor: The background color of child components.\n" +
            "- int textColor: The text color.\n" +
            "- boolean isItalic: If true, the text is italic.\n" +
            "- boolean isBold: If true, the text is bold.\n" +
            "- boolean applyToViewChildren: If true, apply to child views.\n" +
            "- boolean applyToViewGroupChildren: If true, apply to children of view groups.\n" +
            "- boolean applyOrganizationVerticalHorizontal: Apply vertical/horizontal organization.\n" +
            "Return: None")
    public void RoundCorners(
    		AndroidViewComponent component, 
    		float cornerRadius,
    		float borderWidth, 
    		int backgroundColor, 
    		int borderColor, 
    		int childBackgroundColor, 
    		int textColor, 
    		boolean isItalic, 
    		boolean isBold, 
    		boolean applyToViewChildren, 
    		boolean applyToViewGroupChildren, 
    		boolean applyOrganizationVerticalHorizontal
    		) 
    {
        View view = component.getView();
        try {
	        if (view instanceof ViewGroup) {
	            ViewGroup viewGroup = (ViewGroup) view;
	            if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
	            	applyRoundedCornersToViewGroup(viewGroup, cornerRadius, backgroundColor, borderColor, borderWidth, childBackgroundColor, textColor, isItalic, isBold, applyToViewChildren, applyToViewGroupChildren, applyOrganizationVerticalHorizontal);
	            }
	        } else {
	            applyRoundedCornersToView(view, cornerRadius, borderWidth, backgroundColor, borderColor, textColor, isItalic, isBold);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "RoundCorners - Error: " + e.getMessage(), e);
    		ReportError("RoundCorners - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Applies a gradient background to a component.\n" +
            "Parameters:\n" +
            "- component: The component to apply the gradient background.\n" +
            "- int startColor: The starting color of the gradient.\n" +
            "- int endColor: The ending color of the gradient.\n" +
            "- String orientationName: The orientation of the gradient (e.g., TOP_BOTTOM, TR_BL).\n" +
            "Return: None")
    public void ApplyGradientBackground(AndroidViewComponent component, int startColor, int endColor, @Options(GradientOrientation.class) String orientationName) {
        View view = component.getView();
        try {
	        GradientDrawable.Orientation gradientOrientation = convertToGradientOrientation(orientationName);
	        GradientDrawable gradient = new GradientDrawable(gradientOrientation, new int[] { startColor, endColor });
	        gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	        view.setBackground(gradient);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyGradientBackground - Error: " + e.getMessage(), e);
    		ReportError("ApplyGradientBackground - Error: " + e.getMessage());
    	}
    }

    private GradientDrawable.Orientation convertToGradientOrientation(String orientationName) {
        switch (orientationName) {
            case "TOP_BOTTOM":
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case "TR_BL":
                return GradientDrawable.Orientation.TR_BL;
            case "RIGHT_LEFT":
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case "BR_TL":
                return GradientDrawable.Orientation.BR_TL;
            case "BOTTOM_TOP":
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case "BL_TR":
                return GradientDrawable.Orientation.BL_TR;
            case "LEFT_RIGHT":
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case "TL_BR":
                return GradientDrawable.Orientation.TL_BR;
            default:
                return GradientDrawable.Orientation.TOP_BOTTOM; // Valor padrão
        }
    }


    private GradientDrawable.Orientation gradientOrientationFromAngle(int angle) {
        // Converte um ângulo para a orientação do gradiente correspondente
        // Exemplo de implementação simples, precisa ser expandida para cobrir diferentes ângulos
        return GradientDrawable.Orientation.TOP_BOTTOM;
    }

    @SimpleFunction(description = "Applies a selected animation to a component in milliseconds.\n" +
            "Parameters:\n" +
            "- component: The component to animate.\n" +
            "- String effectType: The type of animation effect (e.g., FADE_IN, SLIDE_LEFT).\n" +
            "- int duration: The duration of the animation in milliseconds.\n" +
            "Return: None")
    public void AnimateComponent(final AndroidViewComponent component, @Options(AnimationEffectType.class) String effectType, final int duration) {
        // Convertendo a string do efeito em um valor enum
        final AnimationEffectType effect = AnimationEffectType.fromUnderlyingValue(effectType);

        // Obtém a atividade atual para executar na thread principal
        final Activity activity = (Activity) component.getView().getContext();

        // Executa na thread principal
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = component.getView();
                ViewGroup parent = (ViewGroup) view.getParent();
                int width = parent.getWidth();
                
                try {
	                switch (effect) {
	                    case FADE_IN:
	                        view.setAlpha(0.0f);
	                        view.animate().alpha(1.0f).setDuration(duration).start();
	                        break;
	                    case FADE_OUT:
	                        view.setAlpha(1.0f);
	                        view.animate().alpha(0.0f).setDuration(duration).start();
	                        break;
	                    case SLIDE_LEFT:
	                        view.setTranslationX(width);
	                        view.animate().translationX(0).setDuration(duration).start();
	                        break;
	                    case SLIDE_RIGHT:
	                        view.setTranslationX(-width);
	                        view.animate().translationX(0).setDuration(duration).start();
	                        break;
	                    // Adicione outros casos conforme necessário
	                }
                } catch (Exception e) {
            		if (flagLog) Log.e(LOG_NAME, "AnimateComponent - Error: " + e.getMessage(), e);
            		ReportError("AnimateComponent - Error: " + e.getMessage());
            	}
            }
        });
    }

    
    @SimpleFunction(description = "Applies a custom border to a component.\n" +
            "Parameters:\n" +
            "- component: The component to apply the border.\n" +
            "- int color: The color of the border.\n" +
            "- float width: The width of the border.\n" +
            "- float dashWidth: The width of dashes in the border (for dashed borders).\n" +
            "- float dashGap: The gap between dashes in the border (for dashed borders).\n" +
            "Return: None")
    public void ApplyCustomBorder(AndroidViewComponent component, int color, float width, float dashWidth, float dashGap) {
        View view = component.getView();
        try {
	        GradientDrawable drawable = new GradientDrawable();
	        drawable.setStroke((int) width, color, dashWidth, dashGap);
	        view.setBackground(drawable);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyCustomBorder - Error: " + e.getMessage(), e);
    		ReportError("ApplyCustomBorder - Error: " + e.getMessage());
    	}
    }

    @SimpleFunction(description = "Applies a 3D transformation to a component.\n" +
            "Parameters:\n" +
            "- AndroidViewComponent component: The component to transform.\n" +
            "- float rotationX: The rotation around the X-axis in degrees.\n" +
            "- float rotationY: The rotation around the Y-axis in degrees.\n" +
            "- float depth: The depth of the 3D effect.\n" +
            "Return: None")
    public void Apply3DTransformation(AndroidViewComponent component, float rotationX, float rotationY, float depth) {
        View view = component.getView();
        try {
	        view.setRotationX(rotationX);
	        view.setRotationY(rotationY);
	        view.setTranslationZ(depth); // Requer API 21 ou superior
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "Apply3DTransformation - Error: " + e.getMessage(), e);
    		ReportError("Apply3DTransformation - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Applies a metallic effect to a visual component with customization options.\n" +
            "Parameters:\n" +
            "- component: The component to apply the metallic effect.\n" +
            "- float cornerRadius: The radius of the corners.\n" +
            "- int baseColor: The base color of the metallic effect.\n" +
            "- int borderColor: The color of the border.\n" +
            "- float borderWidth: The width of the border.\n" +
            "- int textColor: The color of the text (if the component supports text).\n" +
            "- float textSize: The size of the text (if the component supports text).\n" +
            "- boolean isItalic: If true, the text is italic (if the component supports text).\n" +
            "- boolean isBold: If true, the text is bold (if the component supports text).\n" +
            "Return: None")
    public void ApplyMetallicEffect(AndroidViewComponent component, 
                                    float cornerRadius, 
                                    int baseColor, 
                                    int borderColor, 
                                    float borderWidth, 
                                    int textColor, 
                                    float textSize, 
                                    boolean isItalic, 
                                    boolean isBold) {
        View view = component.getView();
        try {
	        // Cores para o gradiente
	        int darkerShade = darkenColor(baseColor);
	        int lighterShade = lightenColor(baseColor);
	
	        // Drawable para estado normal
	        GradientDrawable metallicDrawable = createMetallicDrawable(cornerRadius, baseColor, borderColor, borderWidth, lighterShade, darkerShade);
	
	        // Drawable para estado pressionado
	        int pressedColor = darkenColor(baseColor); // Cor mais escura para estado pressionado
	        GradientDrawable pressedDrawable = createMetallicDrawable(cornerRadius, pressedColor, borderColor, borderWidth, lighterShade, darkerShade);
	
	        // StateListDrawable para alternar entre estado normal e pressionado
	        StateListDrawable states = new StateListDrawable();
	        states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
	        states.addState(new int[]{}, metallicDrawable); // Estado padrão
	
	        view.setBackground(states);
	
	        // Configurando o texto, se o componente suportar
	        if (view instanceof TextView) {
	            TextView textView = (TextView) view;
	            textView.setTextColor(textColor);
	            textView.setTextSize(textSize);
	
	            int style = isItalic && isBold ? Typeface.BOLD_ITALIC :
	                        isItalic ? Typeface.ITALIC :
	                        isBold ? Typeface.BOLD : Typeface.NORMAL;
	            textView.setTypeface(textView.getTypeface(), style);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyMetallicEffect - Error: " + e.getMessage(), e);
    		ReportError("ApplyMetallicEffect - Error: " + e.getMessage());
    	}
    }
    
    private GradientDrawable createMetallicDrawable(float cornerRadius, int baseColor, int borderColor, float borderWidth, int lighterShade, int darkerShade) {
        GradientDrawable drawable = new GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, 
            new int[]{lighterShade, baseColor, darkerShade}
        );
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(cornerRadius);
        if (borderWidth > 0) {
            drawable.setStroke((int) borderWidth, borderColor);
        }
        return drawable;
    }
    
    @SimpleFunction(description = "Applies a metallic effect to all components within a container and its subcontainers, except linear layouts.\n" +
            "Parameters:\n" +
            "- AndroidViewComponent container: The container to apply the metallic effect.\n" +
            "- float cornerRadius: The radius of the corners.\n" +
            "- int baseColor: The base color of the metallic effect.\n" +
            "- int borderColor: The color of the border.\n" +
            "- float borderWidth: The width of the border.\n" +
            "- int textColor: The color of the text (if the components support text).\n" +
            "- float textSize: The size of the text (if the components support text).\n" +
            "- boolean isItalic: If true, the text is italic (if the components support text).\n" +
            "- boolean isBold: If true, the text is bold (if the components support text).\n" +
            "Return: None")
    public void ApplyMetallicEffectToAllViews(AndroidViewComponent container, 
                                              float cornerRadius, 
                                              int baseColor, 
                                              int borderColor, 
                                              float borderWidth, 
                                              int textColor, 
                                              float textSize, 
                                              boolean isItalic, 
                                              boolean isBold,
                                              boolean applyToView,
                                              boolean applyToViewGroup
                                              ) 
    {
        View view = container.getView();
        if (view instanceof ViewGroup) {
        	try {
        		applyMetallicEffectRecursively((ViewGroup) view, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold, applyToView, applyToViewGroup);
        	} catch (Exception e) {
        		if (flagLog) Log.e(LOG_NAME, "ApplyMetallicEffectToAllViews - Error: " + e.getMessage(), e);
        		ReportError("ApplyMetallicEffectToAllViews - Error: " + e.getMessage());
        	}
        }
    }

    private void applyMetallicEffectRecursively(ViewGroup viewGroup, 
                                                float cornerRadius, 
                                                int baseColor, 
                                                int borderColor, 
                                                float borderWidth, 
                                                int textColor, 
                                                float textSize, 
                                                boolean isItalic, 
                                                boolean isBold,
                                                boolean applyToView,
                                                boolean applyToViewGroup
                                                )
    {
        for (int i = 0; i < viewGroup.getChildCount(); i++) 
        {
            View child = viewGroup.getChildAt(i);

            // Se o filho é um LinearLayout, pula a aplicação do efeito metálico
            // mas continua a iteração recursiva nos filhos dele
            /*
            if (child instanceof LinearLayout && applyToViewGroup) {
                applyMetallicEffectRecursively((LinearLayout) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold);
            }*/

            if (flagLog) Log.i(LOG_NAME, applyToViewGroup + " and " + applyToView);
            if (!applyToViewGroup && applyToView) {
            	if (child instanceof LinearLayout || child instanceof ViewGroup) {
                	applyMetallicEffectRecursively((ViewGroup) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold, true, false);
                	continue;
                }
            }
            
            if (applyToViewGroup && applyToView) {
            	if (child instanceof LinearLayout || child instanceof ViewGroup) {
            		if (flagLog) Log.i(LOG_NAME, "TRUE and TRUE: child instanceof LenearLayout!");
                	applyMetallicEffectToView((ViewGroup) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold);
                	applyMetallicEffectRecursively((ViewGroup) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold, true, true);
                } 
            	
            	if (child instanceof View) {
                    // Aplica efeito metálico a outros tipos de View
            		applyMetallicEffectToView(child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold);
                } 
            } 
            
            if (applyToViewGroup) {
            	if (child instanceof LinearLayout || child instanceof ViewGroup) {
            		if (flagLog) Log.i(LOG_NAME, "TRUE and FALSE: child instanceof LinearLayout || child instanceof ViewGroup!");
                	applyMetallicEffectToView((ViewGroup) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold);
                	applyMetallicEffectRecursively((ViewGroup) child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold, false, true);
                } 
            	
            } 
            
            if (applyToView) {
            	if (child instanceof View) {
            		if (flagLog) Log.i(LOG_NAME, "FALSE and TRUE: child instanceof View!");
                    // Aplica efeito metálico a outros tipos de View
            		applyMetallicEffectToView(child, cornerRadius, baseColor, borderColor, borderWidth, textColor, textSize, isItalic, isBold);
                }
            }
        }
    }
    
    private void applyMetallicEffectToView(View view, 
            float cornerRadius, 
            int baseColor, 
            int borderColor, 
            float borderWidth, 
            int textColor, 
            float textSize, 
            boolean isItalic, 
            boolean isBold) 
    {
        // Drawable para estado normal
        GradientDrawable metallicDrawable = createMetallicDrawable(cornerRadius, baseColor, borderColor, borderWidth);

        // Drawable para estado pressionado
        int pressedColor = darkenColor(baseColor); // Uma cor mais escura para o estado pressionado
        GradientDrawable pressedDrawable = createMetallicDrawable(cornerRadius, pressedColor, borderColor, borderWidth);

        // StateListDrawable que muda entre estados normal e pressionado
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        states.addState(new int[]{}, metallicDrawable); // Estado padrão

        view.setBackground(states);

        // Configurações de texto para TextView
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            int style = Typeface.NORMAL;
            if (isItalic && isBold) {
                style = Typeface.BOLD_ITALIC;
            } else if (isItalic) {
                style = Typeface.ITALIC;
            } else if (isBold) {
                style = Typeface.BOLD;
            }
            textView.setTypeface(textView.getTypeface(), style);
        }

        view.invalidate();
    }

    private GradientDrawable createMetallicDrawable(float cornerRadius, int baseColor, int borderColor, float borderWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(cornerRadius);

        int darkerShade = darkenColor(baseColor);
        int lighterShade = lightenColor(baseColor);
        drawable.setColors(new int[]{lighterShade, baseColor, darkerShade});

        if (borderWidth > 0) {
            drawable.setStroke((int) borderWidth, borderColor);
        }

        return drawable;
    }


    private int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // Converte a cor para o formato HSV
        hsv[2] *= 0.8f; // Diminui a luminosidade (value) em 20%
        return Color.HSVToColor(hsv); // Converte de volta para o formato de cor e retorna
    }

    private int lightenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // Converte a cor para o formato HSV
        hsv[2] *= 1.2f; // Aumenta a luminosidade (value) em 20%
        hsv[2] = Math.min(hsv[2], 1.0f); // Garante que o valor não exceda 1
        return Color.HSVToColor(hsv); // Converte de volta para o formato de cor e retorna
    }
    
    @SimpleFunction(description = "Applies a blur effect to the background of a visual component.")
    public void ApplyBlurEffect(AndroidViewComponent component, float radius) {
        View view = component.getView();
        try {
	        // Verifique se a API do Android suporta a funcionalidade de desfoque
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
	            // Cria um Bitmap para o fundo
	            view.setDrawingCacheEnabled(true);
	            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
	            view.setDrawingCacheEnabled(false);
	
	            // Aplica o desfoque ao Bitmap
	            Bitmap blurredBitmap = blurBitmap(bitmap, radius, view.getContext());
	
	            // Define o Bitmap desfocado como plano de fundo
	            view.setBackground(new BitmapDrawable(view.getResources(), blurredBitmap));
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyBlurEffect - Error: " + e.getMessage(), e);
    		ReportError("ApplyBlurEffect - Error: " + e.getMessage());
    	}
    }

    private Bitmap blurBitmap(Bitmap bitmap, float radius, Context context) {
        // Cria um Bitmap vazio com as mesmas dimensões
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap);

        // Inicializa um RenderScript
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // Aloca memória para RenderScript
        Allocation tmpIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // Define o raio do desfoque
        blurScript.setRadius(radius);
        
        // Executa o RenderScript
        blurScript.setInput(tmpIn);
        blurScript.forEach(tmpOut);

        // Copia o resultado final para o Bitmap de saída
        tmpOut.copyTo(outputBitmap);

        // Libera os recursos do RenderScript
        rs.destroy();

        return outputBitmap;
    }
    
    @SimpleFunction(description = "Applies a ripple effect to all child components within a container and its subcontainers, except for linear layouts.")
    public void ApplyRippleEffectToAllViews(AndroidViewComponent container, final int rippleColor) {
        final View view = container.getView();

        // Obtém a atividade atual para executar na thread principal
        Activity activity = (Activity) view.getContext();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (view instanceof ViewGroup) {
                	try {
                		applyRippleEffectToViewGroup((ViewGroup) view, rippleColor);
                	} catch (Exception e) {
                		if (flagLog) Log.e(LOG_NAME, "ApplyRippleEffectToAllViews - Error: " + e.getMessage(), e);
                		ReportError("ApplyRippleEffectToAllViews - Error: " + e.getMessage());
                	}
                }
            }
        });
    }

    private void applyRippleEffectToViewGroup(ViewGroup viewGroup, int rippleColor) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof LinearLayout) {
            	applyRippleEffectToViewGroup((ViewGroup) child, rippleColor);
                continue;
            }

            if (child instanceof ViewGroup) {
                // Aplicar recursivamente o efeito ripple aos subfilhos
                applyRippleEffectToViewGroup((ViewGroup) child, rippleColor);
            } else {
                // Aplicar o efeito ripple ao componente View
                createRippleEffectOnView(child, rippleColor);
            }
        }
    }

    private void createRippleEffectOnView(View view, int rippleColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList colorStateList = ColorStateList.valueOf(rippleColor);
            Drawable background = view.getBackground();
            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, background, null);
            view.setBackground(rippleDrawable);
        }
    }


    @SimpleFunction(description = "Creates a ripple effect on a component when touched.")
    public void CreateRippleEffect(AndroidViewComponent component, int rippleColor) {
        View view = component.getView();
        try {
	        // Verifique se a API do Android suporta RippleDrawable
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	            // Cria um RippleDrawable
	            ColorStateList colorStateList = ColorStateList.valueOf(rippleColor);
	            Drawable background = view.getBackground();
	            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, background, null);
	
	            // Define o RippleDrawable como plano de fundo
	            view.setBackground(rippleDrawable);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "CreateRippleEffect - Error: " + e.getMessage(), e);
    		ReportError("CreateRippleEffect - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Applies a textured background to the component.")
    public void ApplyTexturedBackground(AndroidViewComponent component, @Asset String texturePath) {
        View view = component.getView();
        InputStream inputStream = null;
        try {
            inputStream = view.getContext().getAssets().open(texturePath);
            Drawable textureDrawable = Drawable.createFromStream(inputStream, null);
            view.setBackground(textureDrawable);
        } catch (IOException e) {
            if (flagLog) Log.e(LOG_NAME, "Error loading texture! Error: " + e.getMessage(), e);
            ReportError("Error loading texture: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
            		if (flagLog) Log.e(LOG_NAME, "Error: " + e.getMessage(), e);
            		ReportError("Error: " + e.getMessage());
                }
            }
        }
    }


    @SimpleFunction(description = "Applies a glow effect around the component.")
    public void ApplyGlowEffect(AndroidViewComponent component, int glowColor, float glowRadius) {
        View view = component.getView();
        try {
	        // Define as propriedades da sombra
	        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	        Paint paint = new Paint();
	        paint.setColor(glowColor);
	        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));
	
	        // Cria um novo Drawable para aplicar o efeito de brilho
	        Drawable background = view.getBackground();
	        if (background instanceof ShapeDrawable) {
	            ((ShapeDrawable) background).getPaint().set(paint);
	        } else if (background instanceof GradientDrawable) {
	            // Para GradientDrawable, é necessário um wrapper ou uma abordagem customizada
	            // Por exemplo, pode-se criar um Bitmap com o GradientDrawable e depois aplicar o BlurMaskFilter
	            // e configurar esse Bitmap como plano de fundo da view
	        } else if (background instanceof ColorDrawable) {
	            ColorDrawable colorDrawable = (ColorDrawable) background;
	            ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
	            shapeDrawable.getPaint().setColor(colorDrawable.getColor());
	            shapeDrawable.getPaint().set(paint);
	            view.setBackground(shapeDrawable);
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyGlowEffect - Error: " + e.getMessage(), e);
    		ReportError("ApplyGlowEffect - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Applies rounded corners and a press effect to a visual component.")
    public void ApplyPressEffect(AndroidViewComponent component, 
                                 int normalBackgroundColor, 
                                 int pressedBackgroundColor 
                                 )
    {
        View view = component.getView();
        try {
	        // Drawable para o estado normal
	        GradientDrawable normalDrawable = new GradientDrawable();
	        normalDrawable.setColor(normalBackgroundColor);
	
	        // Drawable para o estado pressionado
	        GradientDrawable pressedDrawable = new GradientDrawable();
	        pressedDrawable.setColor(pressedBackgroundColor);
	
	        // StateListDrawable para alternar entre os estados normal e pressionado
	        StateListDrawable states = new StateListDrawable();
	        states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
	        states.addState(new int[]{}, normalDrawable);
	
	        // Aplica o StateListDrawable como plano de fundo do componente
	        view.setBackground(states);
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ApplyPressEffect - Error: " + e.getMessage(), e);
    		ReportError("ApplyPressEffect - Error: " + e.getMessage());
    	}
    }
    
    @SimpleFunction(description = "Applies a press effect to all child components within a container and its subcontainers, except for linear layouts.")
    public void ApplyPressEffect(AndroidViewComponent container, 
            int normalBackgroundColor, 
            int pressedBackgroundColor, 
            boolean applyToViewChildren, 
            boolean applyToViewGroupChildren
            )
    {
		View view = container.getView();
		if (view instanceof ViewGroup) {
			try {
				applyPressEffectToViewGroup((ViewGroup) view, normalBackgroundColor, pressedBackgroundColor, applyToViewChildren, applyToViewGroupChildren);
			} catch (Exception e) {
	    		if (flagLog) Log.e(LOG_NAME, "ApplyPressEffect - Error: " + e.getMessage(), e);
	    		ReportError("ApplyPressEffect - Error: " + e.getMessage());
	    	}
		}
    }
    
    @SimpleFunction(description = "Applies a glow effect around all child components within a container and its subcontainers, except for linear layouts.")
    public void ApplyGlowEffectToAllViews(AndroidViewComponent container, int glowColor, float glowRadius) {
        View view = container.getView();
        if (view instanceof ViewGroup) {
        	try {
        		applyGlowEffectRecursively((ViewGroup) view, glowColor, glowRadius);
        	} catch (Exception e) {
	    		if (flagLog) Log.e(LOG_NAME, "ApplyGlowEffectToAllViews - Error: " + e.getMessage(), e);
	    		ReportError("ApplyGlowEffectToAllViews - Error: " + e.getMessage());
	    	}
        }
    }
    
    @SimpleFunction(description = "Apply a specified shape to all components within a container and its subcontainers, except for linear layouts.")
    public void ApplyShapeToAllComponents(AndroidViewComponent container, final ShapeType shapeType, final int defaultColor) {
        final View view = container.getView();
        
        // Obtém a atividade atual para executar na thread principal
        Activity activity = (Activity) view.getContext();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (view instanceof ViewGroup) {
                	try {
                		applyShapeToViewGroup((ViewGroup) view, shapeType, defaultColor);
                	} catch (Exception e) {
        	    		if (flagLog) Log.e(LOG_NAME, "ApplyShapeToAllComponents - Error: " + e.getMessage(), e);
        	    		ReportError("ApplyShapeToAllComponents - Error: " + e.getMessage());
        	    	}
                }
            }
        });
    }

    private void applyShapeToViewGroup(ViewGroup viewGroup, ShapeType shapeType, int defaultColor) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof LinearLayout) {
            	applyShapeToViewGroup((ViewGroup) child, shapeType, defaultColor);
                continue;
            }

            if (child instanceof ViewGroup) {
                // Aplicar recursivamente a mudança de forma aos subfilhos
                applyShapeToViewGroup((ViewGroup) child, shapeType, defaultColor);
            } else {
                // Aplicar a mudança de forma ao componente View
                changeShapeOfView(child, shapeType, defaultColor);
            }
        }
    }

    private void changeShapeOfView(final View view, final ShapeType shapeType, final int defaultColor) {
        view.post(new Runnable() {
            @Override
            public void run() {
                int width = view.getWidth();
                int height = view.getHeight();

                // Verifica se as dimensões estão disponíveis
                if (width == 0 || height == 0) {
                    return; // Dimensões não disponíveis, não é possível aplicar a forma
                }

                Drawable originalDrawable = view.getBackground();
                int backColor = defaultColor;
                if (originalDrawable instanceof ColorDrawable) {
                    backColor = ((ColorDrawable) originalDrawable).getColor();
                }

                Drawable shapeDrawable = createShapeDrawable(shapeType, width, height, backColor);
                if (shapeDrawable != null) {
                    view.setBackground(shapeDrawable);
                }
            }
        });
    }

    
    @SimpleFunction(description = "Changes the shape of a visual component to a specified shape.")
    public void ChangeComponentShape(AndroidViewComponent component, @Options(ShapeType.class) ShapeType shapeType, int defaultColor) {
        View view = component.getView();
        Drawable originalDrawable = view.getBackground(); // Obtém o Drawable original
        
        try {
	        // Certifique-se de que width e height estão disponíveis
	        int width = view.getWidth();
	        int height = view.getHeight();
	
	        int backColor = defaultColor; // Cor padrão se não for ColorDrawable
	        if (originalDrawable instanceof ColorDrawable) {
	            backColor = ((ColorDrawable) originalDrawable).getColor();
	        }
	
	        Drawable shapeDrawable = createShapeDrawable(shapeType, width, height, backColor);
	
	        if (shapeDrawable != null) {
	            view.setBackground(shapeDrawable);
	        } else {
	            ReportError("Shape type not supported: " + shapeType.toString());
	        }
        } catch (Exception e) {
    		if (flagLog) Log.e(LOG_NAME, "ChangeComponentShape - Error: " + e.getMessage(), e);
    		ReportError("ChangeComponentShape - Error: " + e.getMessage());
    	}
    }

    private Drawable createShapeDrawable(ShapeType shapeType, int width, int height, int backColor) {
        switch (shapeType) {
            case CIRCLE:
                return createCircleDrawable(width, height, backColor);
            case TRIANGLE:
                return createTriangleDrawable(width, height, backColor);
            case RECTANGLE:
                return createRectangleDrawable(width, height, backColor);
            case PENTAGON:
                return createPentagonDrawable(width, height, backColor);
            case HEXAGON:
                return createHexagonDrawable(width, height, backColor);
            case STAR:
                return createStarDrawable(width, height, backColor);
            case HEART:
                return createHeartDrawable(width, height, backColor);
            default:
            	return createRectangleDrawable(width, height, backColor);
        }
    }


    private Drawable createCircleDrawable(int width, int height, int backColor) {
        // Implementação para criar um Drawable circular
        ShapeDrawable circleDrawable = new ShapeDrawable(new OvalShape());
        circleDrawable.getPaint().setColor(backColor);
        circleDrawable.setIntrinsicWidth(width);
        circleDrawable.setIntrinsicHeight(height);
        return circleDrawable;
    }

    private Drawable createTriangleDrawable(int width, int height, int backColor) {
        // Implementação para criar um Drawable triangular
        Path trianglePath = new Path();
        trianglePath.moveTo(width / 2, 0);
        trianglePath.lineTo(width, height);
        trianglePath.lineTo(0, height);
        trianglePath.close();

        ShapeDrawable triangleDrawable = new ShapeDrawable(new PathShape(trianglePath, width, height));
        triangleDrawable.getPaint().setColor(backColor); // Cor verde para exemplo
        return triangleDrawable;
    }

    private Drawable createRectangleDrawable(int width, int height, int backColor) {
        // Implementação para criar um Drawable retangular
        ShapeDrawable rectangleDrawable = new ShapeDrawable(new RectShape());
        rectangleDrawable.getPaint().setColor(backColor); // Cor vermelha para exemplo
        rectangleDrawable.setIntrinsicWidth(width);
        rectangleDrawable.setIntrinsicHeight(height);
        return rectangleDrawable;
    }
    
    private Drawable createPentagonDrawable(int width, int height, int backColor) {
        Path path = new Path();
        path.moveTo(width / 2f, 0);
        path.lineTo(width, height * 0.4f);
        path.lineTo(width * 0.8f, height);
        path.lineTo(width * 0.2f, height);
        path.lineTo(0, height * 0.4f);
        path.close();

        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, width, height));
        drawable.getPaint().setColor(backColor); // Cor magenta para exemplo
        return drawable;
    }

    private Drawable createHexagonDrawable(int width, int height, int backColor) {
        Path path = new Path();
        path.moveTo(width / 2f, 0);
        path.lineTo(width, height / 4f);
        path.lineTo(width, height * 3 / 4f);
        path.lineTo(width / 2f, height);
        path.lineTo(0, height * 3 / 4f);
        path.lineTo(0, height / 4f);
        path.close();

        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, width, height));
        drawable.getPaint().setColor(backColor); // Cor amarela para exemplo
        return drawable;
    }

    private Drawable createStarDrawable(int width, int height, int backColor) {
        Path path = new Path();
        path.moveTo(width / 2f, 0);
        path.lineTo(width * 0.62f, height * 0.38f);
        path.lineTo(width, height * 0.38f);
        path.lineTo(width * 0.68f, height * 0.62f);
        path.lineTo(width * 0.8f, height);
        path.lineTo(width / 2f, height * 0.76f);
        path.lineTo(width * 0.2f, height);
        path.lineTo(width * 0.32f, height * 0.62f);
        path.lineTo(0, height * 0.38f);
        path.lineTo(width * 0.38f, height * 0.38f);
        path.close();

        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, width, height));
        drawable.getPaint().setColor(backColor); // Cor ciano para exemplo
        return drawable;
    }

    private Drawable createHeartDrawable(int width, int height, int backColor) {
        Path path = new Path();
        path.moveTo(width / 2f, height / 4f);
        path.cubicTo(width * 5 / 6f, 0, width, height / 3f, width / 2f, height);
        path.moveTo(width / 2f, height / 4f);
        path.cubicTo(width / 6f, 0, 0, height / 3f, width / 2f, height);

        ShapeDrawable drawable = new ShapeDrawable(new PathShape(path, width, height));
        drawable.getPaint().setColor(backColor); // Cor vermelha para exemplo
        return drawable;
    }

    
    /*
     * EVENTS
     */
    
    @SimpleEvent(description = "Report an error with a custom message")
    public void ReportError(String errorMessage) {
        EventDispatcher.dispatchEvent(this, "ReportError", errorMessage);
    }

    /*
     * PRIVATE METHODS
     */

    private void applyRoundedCornersToView(
    		View view, 
    		float cornerRadius,
    		float borderWidth,
    		int backgroundColor, 
    		int borderColor,  
    		int textColor, 
    		boolean isItalic, 
    		boolean isBold
    		) 
    {
        GradientDrawable roundedDrawable = new GradientDrawable();
        roundedDrawable.setShape(GradientDrawable.RECTANGLE);
        roundedDrawable.setCornerRadius(cornerRadius);
        roundedDrawable.setColor(backgroundColor);
        roundedDrawable.setStroke((int) borderWidth, borderColor);

        view.setBackground(roundedDrawable);

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(textColor);
            int style = Typeface.NORMAL;
            if (isItalic && isBold) {
                style = Typeface.BOLD_ITALIC;
            } else if (isItalic) {
                style = Typeface.ITALIC;
            } else if (isBold) {
                style = Typeface.BOLD;
            }
            textView.setTypeface(textView.getTypeface(), style);
        }

        view.invalidate();
    }

    private void applyRoundedCornersToViewGroup(ViewGroup viewGroup, float cornerRadius, int backgroundColor, int borderColor, float borderWidth, int childBackgroundColor, int textColor, boolean isItalic, boolean isBold, boolean applyToViewChildren, boolean applyToViewGroupChildren, boolean applyOrganizationVerticalHorizontal) {
        if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
            applyRoundedCornersToView(viewGroup, cornerRadius, borderWidth, backgroundColor, borderColor, textColor, isItalic, isBold);
        }

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup && applyToViewGroupChildren) {
            	if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
            		applyRoundedCornersToViewGroup((ViewGroup) child, cornerRadius, childBackgroundColor, borderColor, borderWidth, childBackgroundColor, textColor, isItalic, isBold, applyToViewChildren, applyToViewGroupChildren, applyOrganizationVerticalHorizontal);
            	}
            } else if (applyToViewChildren) {
                applyRoundedCornersToView(child, cornerRadius, borderWidth, childBackgroundColor, borderColor, textColor, isItalic, isBold);
            }
        }
    }

    private void apply3DEffectToView(
    		View view, 
    		float cornerRadius,
    		float borderWidth, 
    		int backgroundColor, 
    		int borderColor,
    		int shadowColor, 
    		int elevation, 
    		int textColor, 
    		boolean isItalic, 
    		boolean isBold
    		) 
    {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(cornerRadius);
        drawable.setColor(backgroundColor);
        drawable.setStroke((int) borderWidth, borderColor);

        view.setBackground(drawable);

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(textColor);
            int style = Typeface.NORMAL;
            if (isItalic && isBold) {
                style = Typeface.BOLD_ITALIC;
            } else if (isItalic) {
                style = Typeface.ITALIC;
            } else if (isBold) {
                style = Typeface.BOLD;
            }
            textView.setTypeface(textView.getTypeface(), style);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        }

        view.invalidate();
    }

    private void apply3DEffectToViewGroup(
    		ViewGroup viewGroup, 
    		float cornerRadius,
    		float borderWidth,
    		int backgroundColor, 
    		int borderColor, 
    		int shadowColor, 
    		int elevation, 
    		int textColor, 
    		boolean isItalic, 
    		boolean isBold, 
    		boolean applyToViewChildren, 
    		boolean applyToViewGroupChildren, 
    		boolean applyOrganizationVerticalHorizontal
    		) 
    {
        if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
            apply3DEffectToView(viewGroup, cornerRadius, borderWidth, backgroundColor, borderColor, shadowColor, elevation, textColor, isItalic, isBold);
        }

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup && applyToViewGroupChildren) {
            	if (applyOrganizationVerticalHorizontal || !(viewGroup instanceof LinearLayout)) {
            		apply3DEffectToViewGroup((ViewGroup) child, cornerRadius, borderWidth, backgroundColor, borderColor, shadowColor, elevation, textColor, isItalic, isBold, applyToViewChildren, applyToViewGroupChildren, applyOrganizationVerticalHorizontal);
            	}
            } else if (applyToViewChildren) {
                apply3DEffectToView(child, cornerRadius, borderWidth, backgroundColor, borderColor, shadowColor, elevation, textColor, isItalic, isBold);
            }
        }
    }
    
    //Filter
    private ColorMatrixColorFilter createColorFilter(String filterType) {
        switch (filterType) {
            case "grayscale":
                return new ColorMatrixColorFilter(new float[]{
                    0.33f, 0.33f, 0.33f, 0, 0,
                    0.33f, 0.33f, 0.33f, 0, 0,
                    0.33f, 0.33f, 0.33f, 0, 0,
                    0,     0,     0,     1, 0
                });
            case "sepia":
                return new ColorMatrixColorFilter(new float[]{
                    0.393f, 0.769f, 0.189f, 0, 0,
                    0.349f, 0.686f, 0.168f, 0, 0,
                    0.272f, 0.534f, 0.131f, 0, 0,
                    0,      0,      0,      1, 0
                });
            case "invert":
                return new ColorMatrixColorFilter(new float[]{
                    -1,  0,  0, 0, 255,
                     0, -1,  0, 0, 255,
                     0,  0, -1, 0, 255,
                     0,  0,  0, 1,   0
                });
            case "none":
            default:
                return null;
        }
    }
    //Filter
    
    //Prees effect
    private void applyPressEffectToView(View view, 
            float cornerRadius, 
            int normalBackgroundColor, 
            int pressedBackgroundColor, 
            int borderColor, 
            float borderWidth, 
            int textColor, 
            boolean isItalic, 
            boolean isBold
            )
    {
		GradientDrawable normalDrawable = new GradientDrawable();
		normalDrawable.setShape(GradientDrawable.RECTANGLE);
		normalDrawable.setCornerRadius(cornerRadius);
		normalDrawable.setColor(normalBackgroundColor);
		normalDrawable.setStroke((int) borderWidth, borderColor);
		
		GradientDrawable pressedDrawable = new GradientDrawable();
		pressedDrawable.setShape(GradientDrawable.RECTANGLE);
		pressedDrawable.setCornerRadius(cornerRadius);
		pressedDrawable.setColor(pressedBackgroundColor);
		pressedDrawable.setStroke((int) borderWidth, borderColor);
		
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
		states.addState(new int[]{}, normalDrawable);
		
		view.setBackground(states);
		
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			textView.setTextColor(textColor);
			int style = isItalic && isBold ? Typeface.BOLD_ITALIC : isItalic ? Typeface.ITALIC : isBold ? Typeface.BOLD : Typeface.NORMAL;
			textView.setTypeface(textView.getTypeface(), style);
		}
	}
    
    private void applyPressEffectToView(View view, 
            int normalBackgroundColor, 
            int pressedBackgroundColor
            )
    {
		Drawable originalBackground = view.getBackground();
		
		// Cria um novo Drawable para o estado pressionado
		ColorDrawable pressedDrawable = new ColorDrawable(pressedBackgroundColor);
		
		// Usa StateListDrawable para mudar entre os estados normal e pressionado
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
		states.addState(new int[]{}, originalBackground); // Usa o plano de fundo original para o estado normal
		
		view.setBackground(states);
		}
    
    private void applyPressEffectToViewGroup(ViewGroup viewGroup, 
            int normalBackgroundColor, 
            int pressedBackgroundColor, 
            boolean applyToViewChildren, 
            boolean applyToViewGroupChildren
            )
    {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View child = viewGroup.getChildAt(i);
			if (child instanceof ViewGroup && applyToViewGroupChildren) {
				applyPressEffectToViewGroup((ViewGroup) child, normalBackgroundColor, pressedBackgroundColor, applyToViewChildren, applyToViewGroupChildren);
			} else if (applyToViewChildren && !(child instanceof LinearLayout)) {
				applyPressEffectToView(child, normalBackgroundColor, pressedBackgroundColor);
			}
		}
    }
    //Press effect
    
    //Glow effect
    private void applyGlowEffectRecursively(ViewGroup viewGroup, int glowColor, float glowRadius) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (!(child instanceof LinearLayout)) {
                applyGlowEffectToView(child, glowColor, glowRadius);
            }

            if (child instanceof ViewGroup) {
                applyGlowEffectRecursively((ViewGroup) child, glowColor, glowRadius);
            }
        }
    }

    private void applyGlowEffectToView(View view, int glowColor, float glowRadius) {
        // Define as propriedades da sombra
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Paint paint = new Paint();
        paint.setColor(glowColor);
        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));

        // Cria um novo Drawable para aplicar o efeito de brilho
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().set(paint);
        } else if (background instanceof GradientDrawable) {
            // Para GradientDrawable, é necessário um wrapper ou uma abordagem customizada
            // Por exemplo, pode-se criar um Bitmap com o GradientDrawable e depois aplicar o BlurMaskFilter
            // e configurar esse Bitmap como plano de fundo da view
        } else if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
            shapeDrawable.getPaint().setColor(colorDrawable.getColor());
            shapeDrawable.getPaint().set(paint);
            view.setBackground(shapeDrawable);
        }
    }
    //Glow effect
}
