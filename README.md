# BeautifyComponents Extension for MIT App Inventor

## Overview
The `BeautifyComponents` extension, part of the `com.bosonshiggs.beautifycomponents` package, enhances the visual appearance of components in MIT App Inventor projects. With features like shadow effects, opacity adjustments, rotations, and 3D transformations, this extension offers a wide range of functionalities to beautify user interfaces.

---

## Public Methods

### AddShadowEffect
- **Description**: Applies a shadow effect to the corners of a visual component.
- **Parameters**: 
  - `component`: The component to apply the shadow effect.
  - `shadowColor`: The color of the shadow (int).
  - `shadowRadius`: The radius of the shadow (float).
  - `dx`: The horizontal offset of the shadow (float).
  - `dy`: The vertical offset of the shadow (float).
- **Return**: None

### AddShadowEffectToAllViews
- **Description**: Applies a shadow effect to all components within a container and its subcontainers, excluding linear layouts.
- **Parameters**: Similar to `AddShadowEffect`, but targets a container.
- **Return**: None

### ChangeOpacity
- **Description**: Changes the opacity of a visual component.
- **Parameters**: 
  - `component`: The component to change opacity.
  - `opacity`: Desired opacity level (float, 0.0 to 1.0).
- **Return**: None

### RotateComponent
- **Description**: Rotates a visual component.
- **Parameters**:
  - `component`: The component to rotate.
  - `angle`: Rotation angle in degrees (float).
- **Return**: None

### ScaleComponent
- **Description**: Scales a visual component.
- **Parameters**:
  - `component`: The component to scale.
  - `scaleX`: Scale factor in the X direction (float).
  - `scaleY`: Scale factor in the Y direction (float).
- **Return**: None

### ApplyColorFilter
- **Description**: Applies a color filter to a visual component.
- **Parameters**:
  - `component`: Component to apply the color filter.
  - `filterType`: Type of color filter (String).
- **Return**: None

### GetComponentX / GetComponentY
- **Description**: Returns the X or Y coordinate of a visual component.
- **Parameters**:
  - `component`: The component whose position is queried.
- **Return**: Coordinate value (float).

### OverlayComponentAt
- **Description**: Overlays a component at specific coordinates.
- **Parameters**:
  - `component`: The component to position.
  - `x`: X coordinate (float).
  - `y`: Y coordinate (float).
- **Return**: None

### Apply3DEffect
- **Description**: Applies a 3D effect to a component with customizable options.
- **Parameters**: 
  - `component`, `cornerRadius`, `borderWidth`, `backgroundColor`, `borderColor`, `shadowColor`, `elevation`, `textColor`, `isItalic`, `isBold`, and flags for child view application.
- **Return**: None

### RoundCorners
- **Description**: Rounds corners of a component and adjusts colors.
- **Parameters**: Similar to `Apply3DEffect`.
- **Return**: None

### ApplyGradientBackground
- **Description**: Applies a gradient background to a component.
- **Parameters**:
  - `component`: Component for the background.
  - `startColor`, `endColor`: Gradient colors (int).
  - `orientationName`: Gradient orientation (String).
- **Return**: None

### AnimateComponent
- **Description**: Animates a component.
- **Parameters**:
  - `component`: The component to animate.
  - `effectType`: Type of animation effect (String).
  - `duration`: Duration in milliseconds (int).
- **Return**: None

### ApplyCustomBorder
- **Description**: Applies a custom border to a component.
- **Parameters**:
  - `component`, `color`, `width`, `dashWidth`, `dashGap`.
- **Return**: None

### Apply3DTransformation
- **Description**: Applies a 3D transformation to a component.
- **Parameters**:
  - `component`, `rotationX`, `rotationY`, `depth`.
- **Return**: None

### ApplyMetallicEffect
- **Description**: Applies a metallic effect to a component with customization.
- **Parameters**: 
  - `component`, `cornerRadius`, `baseColor`, `borderColor`, `borderWidth`, `textColor`, `textSize`, `isItalic`, `isBold`.
- **Return**: None

### ApplyBlurEffect
- **Description**: Applies a blur effect to the background of a component.
- **Parameters**:
  - `component`, `radius`.
- **Return**: None

### CreateRippleEffect / ApplyR

ippleEffectToAllViews
- **Description**: Creates a ripple effect on a component or all child components within a container.
- **Parameters**:
  - `component` (or `container` for all views), `rippleColor`.
- **Return**: None

### ApplyTexturedBackground
- **Description**: Applies a textured background to a component.
- **Parameters**:
  - `component`, `texturePath` (String, asset path).
- **Return**: None

### ApplyGlowEffect / ApplyGlowEffectToAllViews
- **Description**: Applies a glow effect around a component or all child components within a container.
- **Parameters**:
  - `component` (or `container` for all views), `glowColor`, `glowRadius`.
- **Return**: None

### ApplyShapeToAllComponents / ChangeComponentShape
- **Description**: Applies a specified shape to all components within a container or changes the shape of a single component.
- **Parameters**:
  - `container` or `component`, `shapeType` (ShapeType enum), `defaultColor`.
- **Return**: None

---

## Events

### ReportError
- **Description**: Reports an error with a custom message.
- **Parameters**:
  - `errorMessage`: Custom error message (String).
- **Trigger**: When an error occurs in any of the methods.

---

## Private Methods
- Additional private helper methods are used within the extension for internal processing and are not exposed to the user.

*Note: This documentation is based on the provided code snippet and aims to describe the public interface of the `BeautifyComponents` extension. Ensure to test each method for specific requirements and compatibility with your project.*