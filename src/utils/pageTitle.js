import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Vue Admin Template'

export function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}

export function setPageTitle(pageTitle) {
  document.title = getPageTitle(pageTitle)
}
